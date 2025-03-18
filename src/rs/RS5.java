package rs;

public class RS5 {
  private int[] exp = new int[512];
  private int[] log = new int[256];

  public RS5() {
    lookUpTable();
    // int[] msgIn={0x40,0xd2,0x75,0x47,0x76,0x17,0x32,0x6,
    // 0x27,0x26,0x96,0xc6,0xc6,0x96,0x70,0xec};
    // System.out.println("msgIn=");
    // printArray(msgIn);
    // int[] msgOut=encodeMsg(msgIn,10);
    // System.out.println("msgOut=");
    // printArray(msgOut);
    // int[] synd=calcSyndromes(msgOut,10);
    // System.out.println("synd=");
    // printArray(synd);
    // System.out.println("testing findErrorLocator");
    // msgOut[0]=6;
    // msgOut[10]=7;
    // msgOut[20]=8;
    // synd=calcSyndromes(msgOut,10);
    // System.out.println("synd=");
    // printArray(synd);
    // System.out.println("testing findErrorLocator");
    // int[] eLoc=findErrorLocator(synd,10);
    // System.out.println("in main eLoc=");
    // printArray(eLoc);
    // System.out.println("testing findErrors");
    // int[] ePos=findErrors(eLoc,msgOut.length);
    // System.out.println("in main ePos=");
    // printArray(ePos);
    // int[] msgCorr=correctErrata(msgOut,synd,ePos);
    // System.out.println("msgCorr=");
    // printArray(msgCorr);
  }

  private void printArray(int[] a) {
    for (int i = 0; i < a.length; i++)
      System.out.printf("%X ", a[i]);
    System.out.println();
  }

  private void lookUpTable() {
    int j = 1;
    for (int i = 0; i < 255; i++) {
      exp[i] = j;
      log[j] = i;
      j = mult2(j, 2);
    }
    for (int i = 255; i < 512; i++) {
      exp[i] = exp[i - 255];
    }
  }

  private int add(int a, int b) {
    return a ^ b;
  }

  private int sub(int a, int b) {
    return a ^ b;
  }

  private int mult2(int a, int b) {
    int c = 0;
    int i = 0;
    while (b >> i > 0) {
      if ((b & (1 << i)) != 0)
        c ^= a << i;
      i += 1;
    }
    i = 7;
    while (c > 0xff) {
      if ((c & (1 << (i + 8))) != 0)
        c ^= 0x11D << i;
      i--;
    }
    return c;
  }

  private int mult(int a, int b) {
    if (a == 0 || b == 0)
      return 0;
    return exp[log[a] + log[b]];
  }

  private int div(int a, int b) {
    if (b == 0)
      System.out.println("div by 0");
    if (a == 0)
      return 0;
    return exp[(log[a] + 255 - log[b]) % 255];
  }

  private int pow(int a, int b) {
    return exp[(log[a] * b + 255) % 255];
  }

  private int inverse(int a) {
    return exp[(255 - log[a]) % 255];
  }

  private int[] polyScale(int[] a, int b) {
    int[] c = new int[a.length];
    for (int i = 0; i < a.length; i++) {
      c[i] = mult(a[i], b);
    }
    return c;
  }

  private int[] polyAdd(int[] a, int[] b) {
    int s = Math.max(a.length, b.length);
    int s1 = s - a.length;
    int s2 = s - b.length;
    int[] c = new int[s];
    for (int i = 0; i < a.length; i++)
      c[i + s1] = a[i];
    for (int i = 0; i < b.length; i++)
      c[i + s2] ^= b[i];
    return c;
  }

  private int[] polyMult(int[] a, int[] b) {
    int[] c = new int[a.length + b.length - 1];
    for (int i = 0; i < b.length; i++) {
      for (int j = 0; j < a.length; j++) {
        c[i + j] ^= mult(a[j], b[i]);
      }
    }
    return c;
  }

  private int polyEval(int[] a, int b) {
    int c = a[0];
    for (int i = 1; i < a.length; i++) {
      c = mult(c, b) ^ a[i];
    }
    return c;
  }

  private int[] generator(int n) {
    int[] c = {1};
    for (int i = 0; i < n; i++) {
      int[] d = {1, pow(2, i)};
      c = polyMult(c, d);
    }
    return c;
  }

  private int[][] polyDiv(int[] a, int[] b) {
    int[][] c = new int[2][];
    int[] d = new int[a.length];
    int[] q = new int[a.length - b.length + 1];
    int[] r = new int[b.length - 1];
    for (int i = 0; i < a.length; i++)
      d[i] = a[i];
    for (int i = 0; i < a.length - b.length + 1; i++) {
      q[i] = d[i];
      int m = d[i];
      for (int j = 0; j < b.length; j++) {
        d[i + j] ^= mult(b[j], m);
      }
    }
    for (int i = 0; i < b.length - 1; i++) {
      r[i] = d[a.length - b.length + 1 + i];
    }
    return new int[][] {q, r};
  }

  private int[] encodeMsg(int[] msgIn, int nsym) {
    int[] msgOut = new int[msgIn.length + nsym];
    int[] gen = generator(nsym);
    for (int i = 0; i < msgIn.length; i++)
      msgOut[i] = msgIn[i];
    int[][] rem = polyDiv(msgOut, gen);
    for (int i = msgIn.length; i < msgOut.length; i++) {
      msgOut[i] = rem[1][i - msgIn.length];
    }
    return msgOut;
  }

  private int[] calcSyndromes(int[] msg, int nsym) {
    int[] synd = new int[nsym + 1];
    for (int i = 1; i < synd.length; i++) {
      synd[i] = polyEval(msg, pow(2, i - 1));
    }
    return synd;
  }

  private int[] findErrataLocator(int[] ePos) {
    int[] eLoc = {1};
    for (int i = 0; i < ePos.length; i++) {
      int[] ia1 = {pow(2, ePos[i]), 0};
      int[] ia2 = {1};
      eLoc = polyMult(eLoc, polyAdd(ia2, ia1));
    }
    return eLoc;
  }

  private int[] findErrorEvaluator(int[] synd, int[] eLoc, int nsym) {
    int[] ia = new int[nsym + 2];
    ia[0] = 1;
    int[] ia2 = polyMult(synd, eLoc);
    int[] rem = polyDiv(polyMult(synd, eLoc), ia)[1];
    return rem;
  }

  private int[] correctErrata(int[] msg, int[] synd, int[] ePos) {
    int[] msgIn = null;
    int[] coefPos = new int[ePos.length];
    for (int i = 0; i < coefPos.length; i++)
      coefPos[i] = msg.length - 1 - ePos[i];
    int[] synd2 = new int[synd.length];
    for (int i = 0; i < synd.length; i++)
      synd2[synd.length - 1 - i] = synd[i];
    int[] eLoc = findErrataLocator(coefPos);
    int[] eEval = findErrorEvaluator(synd2, eLoc, eLoc.length - 1);
    int[] eEval2 = new int[eEval.length];
    for (int i = 0; i < eEval.length; i++)
      eEval2[eEval.length - 1 - i] = eEval[i];
    int[] x = new int[coefPos.length];
    for (int i = 0; i < coefPos.length; i++) {
      int m = 255 - coefPos[i];
      x[i] = pow(2, -m);
    }
    int[] e = new int[msg.length];
    int xlen = x.length;
    for (int i = 0; i < x.length; i++) {
      int xi = inverse(x[i]);
      int[] errLocPrimeTmp = {};
      for (int j = 0; j < xlen; j++) {
        if (j != i) {
          int[] errLocPrimeTmp2 = new int[errLocPrimeTmp.length + 1];
          for (int k = 0; k < errLocPrimeTmp.length; k++)
            errLocPrimeTmp2[k] = errLocPrimeTmp[k];
          errLocPrimeTmp = errLocPrimeTmp2;
          errLocPrimeTmp[errLocPrimeTmp.length - 1] = sub(1, mult(xi, x[j]));
        }
        int errLocPrime = 1;
        for (int k = 0; k < errLocPrimeTmp.length; k++) {
          errLocPrime = mult(errLocPrime, errLocPrimeTmp[k]);
        }
        int y = polyEval(eEval, xi);
        y = mult(x[i], y);
        if (errLocPrime == 0)
          System.out.println("could not find error magnifier");
        int mag = div(y, errLocPrime);
        e[ePos[i]] = mag;
      }
      msgIn = polyAdd(msg, e);
    }
    return msgIn;
  }

  private int[] findErrorLocator(int[] synd, int nsym) {
    int[] eLoc = {1};
    int[] oldLoc = {1};
    int syndShift = synd.length - nsym;
    int eraseCount = 0;
    for (int i = 0; i < nsym - eraseCount; i++) {
      int k = i + syndShift;
      int delta = synd[k];
      for (int j = 1; j < eLoc.length; j++) {
        delta ^= mult(eLoc[eLoc.length - j - 1], synd[k - j]);
      }
      int[] oldLoc2 = new int[oldLoc.length + 1];
      for (int i1 = 0; i1 < oldLoc.length; i1++)
        oldLoc2[i1] = oldLoc[i1];
      oldLoc = oldLoc2;
      if (delta != 0) {
        if (oldLoc.length > eLoc.length) {
          int[] newLoc = polyScale(oldLoc, delta);
          oldLoc = polyScale(eLoc, inverse(delta));
          eLoc = newLoc;
        }
        eLoc = polyAdd(eLoc, polyScale(oldLoc, delta));
      }
    }
    while (eLoc.length > 0 && eLoc[0] == 0) {
      int[] eLoc2 = new int[eLoc.length - 1];
      for (int i1 = 0; i1 < eLoc2.length; i1++)
        eLoc2[i1] = eLoc[i1 + 1];
      eLoc = eLoc2;
    }
    int errs = eLoc.length - 1;
    if (errs * 2 > nsym)
      System.out.println("too many errors to correct");
    return eLoc;
  }

  private int[] findErrors(int[] eLoc, int msgLen) {
    int[] eLoc2 = new int[eLoc.length];
    for (int j = 0; j < eLoc.length; j++)
      eLoc2[eLoc.length - 1 - j] = eLoc[j];
    eLoc = eLoc2;
    int errs = eLoc.length - 1;
    int[] ePos = {};
    for (int i = 0; i < msgLen; i++) {
      if (polyEval(eLoc, pow(2, i)) == 0) {
        int[] ePos2 = new int[ePos.length + 1];
        for (int j = 0; j < ePos.length; j++)
          ePos2[j] = ePos[j];
        ePos = ePos2;
        ePos[ePos.length - 1] = msgLen - 1 - i;
      }
    }
    if (ePos.length != errs)
      System.out.println("too many or few errors");
    return ePos;
  }

  public static void main(String[] args) {
    new RS5();
  }
}
