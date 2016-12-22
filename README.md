# emailGuiTesting
Sample of testing email service functionality:  
1. Successful login    
2. Sending email    
3. Deleting of first letter    

Before run this tests, you must do the following:  
Download webdrivers for browsers, use:  
* [Chrome driver](https://chromedriver.storage.googleapis.com/index.html?path=2.26/). [See details](https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver)
* [Firefox driver](https://github.com/mozilla/geckodriver/releases/tag/v0.11.1). [See details](https://github.com/mozilla/geckodriver)  

and save them in PATH (simplest way - is to store them in emailGuiTesting folder)

Also you have to set system variables, which pointing to using login and password information.  
In Linux add following in file ~/.pam_environment:  
`MAILTESTLOGIN={your_login}  `  
`MAILTESTPASSWORD={your_password}`  
or use your prefered method

In Windows, proceed Start - Control Panel - System - Additional System Parameters - Environment Variables - Create (User's Environment Variables)

Of course, you can write your login and password directly in the code.

That's all! Run:    
`gradle test`  
