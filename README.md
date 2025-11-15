Login mobile app using Kotlin, Jetpack Compose, and Supabase

A small project demonstrating how to use Supabase Authentication in a mobile application, including registration and OTP verification, using the new Navigation Compose.

This project contains a main page, an authentication page, and a success page. It uses repositories and use cases to handle all the logic.
The goal of this project is to help beginners understand how to use Supabase in a mobile application.
Hilt is used for dependency injection.

✅How to use it:

Add the following lines in your local.properties file:

API_KEY=replace_with_your_key

API_URL=replace_with_your_url


Make sure to enable Email Login in your Supabase authentication settings.

Make sure to add the Token placeholder inside your Supabase email templates.

✅How login works:

The user enters their data into the input fields.

When the user clicks submit, a function runs to check whether the email and password are valid.

If the password or email is invalid, an error message—mapped from a list of possible Supabase errors—is displayed to the user.

If the email and password are valid, the request is sent to the server.

If Supabase returns an error, the mapped error message is shown.

If the request succeeds, the user is navigated to the Success Page.

✅How registration works:

The user enters their data into the input fields.

When the user clicks submit, a function checks whether the email, password, and confirmation password are valid.

If all fields are valid, the request is sent to the server.

If Supabase returns an error, a mapped error message is displayed to the user.

If the registration succeeds, the user is navigated to the OTP Page, where they enter the code sent to their email.

The button remains disabled until the user enters a full 6-digit code.

After entering the code, it is sent to Supabase for verification. If the code is valid, the user is logged in and their account is verified.

✅technologies I used:
[Supabase](https://supabase.com)
