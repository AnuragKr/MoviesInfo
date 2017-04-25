# MoviesInfo
The app will:
● Upon launch, present the user with a grid arrangement of movie posters.
● Allow user to change sort order via a setting:
○ The sort order can be by most popular, or by top rated
● Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
•	original title
•	movie poster image thumbnail
•	A plot synopsis (called overview in the api)
•	user rating (called vote_average in the api)
•	release date

#Fetching a data from API

To fetch popular movies, I am using API from themoviedb.org.
○ If you don’t already have an account, you will need to create one in order to request an API Key.
○ In order to request popular movies you will want to request data from the /movie/popular and /movie/top_rated endpoints. An API Key is required.
○ Once you obtain your key, you append it to your HTTP request as a URL parameter like so:
■ http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
○ You will extract the movie id from this request. You will need this in subsequent requests.

