# MusicAdvisor
Simple Java app that works with Spotify's API

## Table of Contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Code example](#code-example)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
The main objective of this project was to learn Java 8 and it's topics like how to interact with the REST API, parse JSON, handle requests. It was made by using Model-View-Controller design pattern.

## Technologies
* Java 8
* GSON 2.8.8 - to parse JSON files
* Apache Commons CLI 1.4 - to parse command-line arguments

## Setup
To launch the project a user must specify _SECRET_CODE_ of the Spotify application as the command-line argument:

```
java -jar MusicAdvisor.jar -secret [value]
```
More info about creating Spotify application and it's secret key at: https://developer.spotify.com

## Features
* Get a list of Spotify new released albums, categories or playlists with their names, artists and links
* Specify authorization server path by using _-access [value]_ argument or API server path by using _-resource [value]_ argument (Spotify's server addresses are default)
* Pagination system - set how many elements are showed on a page by using _-page [value]_ argument

## Code example
After authorization:
```
***********************
     Music advisor
***********************

Commands:
auth - prints the auth link and allows user to use other commands
featured - a list of Spotify-featured playlists with their links fetched from API
new - a list of new albums with artists and links on Spotify
categories - a list of all available categories on Spotify (just their names)
playlists C_NAME - where C_NAME is the name of category. The list contains playlists of this category and their links on Spotify
next - shows next page of a list if it's available
prev - shows previous page of a list if it's available
exit - shuts down the application

>new
Life of a DON
[Don Toliver]
https://open.spotify.com/album/2WmJ5wp5wKBlIJE6FDAIBJ

eat ya veggies
[bbno$]
https://open.spotify.com/album/6iMshsixZe8oMteQdln5kp

Friends That Break Your Heart
[James Blake]
https://open.spotify.com/album/1zNtJFMCNIyT0X19jpcI3j

---PAGE 1 OF 6---

>next
I must apologise
[PinkPantheress]
https://open.spotify.com/album/0m1wwIx9Eoa7fWb9KYh7pt

Working for the Knife
[Mitski]
https://open.spotify.com/album/5z1bA7zhlWlyBgBA2rN4uE

Lo Siento BB:/ (with Bad Bunny & Julieta Venegas)
[Tainy, Bad Bunny, Julieta Venegas]
https://open.spotify.com/album/4589OIFRZp41qbsp7TWFCx

---PAGE 2 OF 6---

>exit
---GOODBYE!---
```

## Status
Completed. Can be expanded in the future (GUI, more features).

## Inspiration
The idea of the project and topics required to learn are from Java Developer course at https://hyperskill.org

## Contact
Created by Adam Klimczuk - adam.klimczuk36@gmail.com
