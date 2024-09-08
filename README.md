# StreamLocations Android Application

StreamLocations is an Android application that uses Google Maps and gRPC to stream real-time location updates. It fetches location data from a server and visualizes it on a map with markers.

## Features

- Real-time streaming of user locations using gRPC
- Integration with Google Maps to visualize location data
- Dependency Injection with Hilt
- MVVM architecture
- Kotlin Coroutines and Flow for managing data streams
- ProtoBuf for efficient data serialization

## Project Structure

- **Data Layer**: Includes the `LocationRepository` and API integration using gRPC.
- **Use Case Layer**: The `FetchLocationUpdatesUseCase` is responsible for fetching location updates based on the user ID.
- **UI Layer**: Contains the `MapScreen` composable, which displays the map and car markers.
- **gRPC Proto Definitions**: The project uses gRPC for communication between the client and the server. Proto files define the `LocationRequest` and `UserLocation` messages, as well as the `LocationService` service.

## Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **Google Maps**
- **gRPC**
- **ProtoBuf**
- **Hilt (Dependency Injection)**
- **Kotlin Coroutines and Flow**
- **Android ViewModel**
- **Dagger Hilt**
- **Material Design 3**

## Getting Started

### Prerequisites

- Android Studio
- Kotlin 1.9.0
- gRPC 1.62.2
- Protobuf Plugin

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-repo/StreamLocations.git
    ```

2. Open the project in Android Studio.

3. Build the project:

    ```bash
    ./gradlew build
    ```

4. Run the project on an emulator or a physical device.

### gRPC and ProtoBuf Integration

The app communicates with the backend server using gRPC. Below are the key components of the gRPC integration:

- `LocationService.proto`: Defines the RPC for fetching location updates.
- `LocationRepository`: Streams location updates from the backend.
- `FetchLocationUpdatesUseCase`: Encapsulates the business logic for fetching locations.

### How It Works

- The app listens to location updates from a gRPC stream using the `LocationService`.
- The location is displayed on a map with a car marker icon, and the map's camera follows the current location.
- You can configure the backend server's URL in the `AppModule` file.

### Code Snippet

```kotlin
class LocationRepository @Inject constructor(
    private val channel: ManagedChannel
) {
    fun streamLocationUpdates(locationRequest: Service.LocationRequest): Flow<Location> {
        val stub = LocationServiceGrpcKt.LocationServiceCoroutineStub(channel).withWaitForReady()

        return stub.getLocation(locationRequest).map { response ->
            Location("gps").apply {
                longitude = response.longitude
                latitude = response.latitude
            }
        }
    }
}
