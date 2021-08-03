# Railmate

An Android App and website to allow people to find train departures and routes in real-time. I integrated with
transport API to provide the data. Routes were set up to allow the app and website to fetch this data. As part of
the project, I wanted to display the train operator’s logo on some pages, but the API didn’t provide this info, so
I built a quick python script to scrape the images from Twitter

## App

This was the first andoid app I tried to build, I completed it of the course of a summer. The app had a couple of main features it would allow you to 
search for any UK stataion and see the next 2 hours of live depatures. It then also gives you the route the train will take.

## [API](https://github.com/m2a9x45/railmate-api) 

The API had a couple of jobs, mainly to provide a nice wrapper to the transport API were the train data comes from. It also hosted the files for
the logos of the different rail operators. 

The API is written in node.js using express as the framework. 

## [Website](https://github.com/m2a9x45/railmate-web) 

The website was build in standard HTML, CSS & JS. It's main purpose was to allow people to download the app along as to act as a landing page. Later on I added the ability 
to veiw the same train data on the website as the app displayied. 

#### Feedback on the project

After I completed the project I posted it to reddit to see what people though of it. The feeback was pretty postive with notes on the data quality being a bit off and 
it being similar top other existing apps. 

[Link to the feedback](https://www.reddit.com/r/uktrains/comments/evapi1/a_website_i_made_that_i_think_you_might_like_its/?utm_source=share&utm_medium=web2x&context=3)




