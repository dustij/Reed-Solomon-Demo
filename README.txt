Run app.jar located in target/dist/

To do list

[ ] Polynomial labels with not show anything until Encode is clicked, then it will fill in coeffecients accordingly
[ ] Restrict the message text field to 12 characters
[x] Replace spinners with custom hex spinners
[ ] Clicking Encode will also populate the hex spinners the same time polynomial labels are generated
[ ] Change color for spinners that are modified by the user
[ ] Ensure formatting and spacing looks nice (spinners are same width, spacing and padding, etc.)
[ ] Add the last section on decoding

questions

I guess I'm still a little confused about how the polynomial looks after dividing by the generator function.
If the syndromes are 0, will the final polynomial still hold 0's in the last 8 places?
My understand is that it won't. After dividing by g(x) the new polynomial will look different.
If thats the case, if there is no corruption then will the message symbols be the original data, or is 
it still encoded, and need decoding before getting the original data?