## 🚀 Java Library for Spotify API

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)  
![Spotify](https://img.shields.io/badge/Spotify-1DB954?style=for-the-badge&logo=spotify&logoColor=white)

A robust, reusable, and modular **Java library** for interacting with the **Spotify API** using **server-to-server authentication**. This library provides an intuitive interface to fetch tracks, albums, artists, and playlists — ideal for backend applications or services that don't require user authorization.

> ⚠️ This library is designed specifically for **server-to-server communication using the Client Credentials Flow**.  
> It does **not support user-based authorization** and therefore **cannot access user-specific data** such as playlists, saved tracks, or listening history.

## ✨ Features

- 🎵 **Retrieve Spotify Data:** Fetch tracks, albums, artists, and playlists effortlessly.
- ⚡ **Easy Integration:** Simplified API calls with built-in request handling.
- 📡 **Real-Time Fetching:** Get up-to-date music data directly from Spotify.
- 📜 **Well-Documented:** Clear and concise documentation for seamless implementation.

## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven for dependency management
- A **Spotify Developer Account** ([Create one here](https://developer.spotify.com/))
- A **Spotify API Key & Client Secret** ([Get credentials](https://developer.spotify.com/dashboard/applications))

### Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.github.gmarcg00.spotify</groupId>
    <artifactId>spotify-api-connector</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 📘 Important Notes

### 🔐 Authentication Scope

This library uses the **Client Credentials Flow**, which is suitable for **server-to-server authentication** only.  
It does **not** include OAuth scopes for end-user access and therefore **cannot interact with user accounts**.

### 📄 Required Configuration

To function correctly, the library expects an `application.yml` file located in the `resources` directory of your project.  
This file defines the base URLs for the Spotify API endpoints.

**Example `application.yml`:**

```yaml
artists: https://api.spotify.com/v1/artists
albums: https://api.spotify.com/v1/albums
albumsNewReleases: https://api.spotify.com/v1/browse/new-releases
episodes: https://api.spotify.com/v1/episodes
tracks: https://api.spotify.com/v1/tracks
playlists: https://api.spotify.com/v1/playlists
markets: https://api.spotify.com/v1/markets
accessToken: https://accounts.spotify.com/api/token
```

📚 [Browse the JavaDoc online](https://tuusuario.github.io/tu-repo/)
