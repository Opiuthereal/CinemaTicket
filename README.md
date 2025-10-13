
# README Project Cinema Ticket Selling - Android Studio

By Opiuthereal and Codastic25  
Big thanks to our teacher Qiong liu (https://github.com/evesiphus)  
Made in 2 weeks during the class period  

## Project Content:

**JAVA and XML**

- `ActivityTarif.java` and its `activity_tarif.xml`  
- `DatabaseActivity.java` and its `activity_database.xml`  
- `DataMainActivity.java`  
- `FilmDetail.java` and its `activity_film_detail.xml`  
- `FilmRecyclerView.java`  
- `FragmentTicketReservation.java` and its `fragment_ticket_reservation.xml`  
- `GridAdapter.java` and its `grid_items.xml`  
- `InfosPersonnelles.java` and its `activity_infos_personnelles.xml`  
- `ItemHoraire.java` and its `item_horaires.xml`  
- `LandingPage.java` and its `activity_landing_page.xml`  
- `MainActivity.java` and its `activity_main.xml` and `item_spinner.xml`  
- `MyAdapter.java` and its `item_view.xml`  
- `MyAdapterHoraire.java`  
- `MyViewHolder.java`  
- `MyViewHolderHoraire.java`  
- `Remerciements.java` and its `activity_remerciements.xml`  
- `ReservationPlaces.java` and its `activity_reservation_places.xml`  
- `TicketResumeAll.java` and its `activity_ticket_resume_all.xml`  

**drawable folder**  
**raw folder** (trailers videos)  
**mipmap folder** (icon and loading page with our logo)  
**values folder** (color, strings, styles, background added)

## CODE EXPLANATION:

### `LandingPage.java` and `activity_landing_page.xml`  
This is the very first screen that only shows once (while the app is loading). It features our logo, used as a loading icon for 3 seconds. After that, it automatically navigates to the now-showing films page: `MainActivity.java`.

![Image](https://github.com/user-attachments/assets/5cd58f6d-4481-41d8-b5ea-2e41478b8d4f)

### `MainActivity.java`, `activity_main.xml`, and `item_spinner.xml`  
The landing page when the app starts. A spinner handles the main categories of cinemas. Different now-showing films for each cinema (displayed horizontally scrollable) appear.

![Image](https://github.com/user-attachments/assets/44d8d2a7-b0a3-44db-b5c7-72259bd1ffd6)

### `MyAdapter.java` and `item_view.xml`  
These files build the now-showing films visible on the main page via `MyViewHolder.java`. It reuses data from `MyViewHolder.java` to populate the RecyclerView. When a film poster is clicked, it passes information (image, title, idCinema) to the next page: `FilmDetail.java`.

### `MyViewHolder.java`  
Initializes the data/info for now-showing films by retrieving their XML IDs from `item_view.xml`.

### `FilmRecyclerView.java`  
Initializes primary film information with methods to handle the film RecyclerView.

### `FilmDetail.java` and `activity_film_detail.xml`  
This page appears when a film is clicked. It shows a description of the selected film, including a video trailer and time slots in item format.  
This page stores some film data to be passed to the fragment: `FragmentTicketReservation.java`.

### `FragmentTicketReservation.java` and `fragment_ticket_reservation.xml`  
This Java class and XML fragment offer a first summary after selecting a showtime. The user can then confirm their ticket, which launches the next page: `ReservationPlaces.java`.

### `ItemHoraire.java` and `item_horaires.xml`  
Initializes the showtime info and provides methods to handle the showtime RecyclerView.

### `MyViewHolderHoraire.java`  
Initializes the data/info for a film’s showtimes by retrieving XML IDs from `item_horaires.xml`.

### `MyAdapterHoraire.java`  
This Java class and XML build the film's showtimes displayed on the film detail page using `MyViewHolderHoraire.java`. When a time slot is clicked, it passes data (day, time, version).

### `ReservationPlaces.java` and `activity_reservation_places.xml`  
This page uses a GridView along with functions from `DataMainActivity.java`. Users can select one or more seats and then confirm to go to the next page: `ActivityTarif.java`.

![Image](https://github.com/user-attachments/assets/a9f27883-fce4-4d80-9673-b48b932b851e)

### `GridAdapter.java` and `grid_items.xml`  
Initializes the seat selection GridView data and provides methods to retrieve and manage the grid.

### `ActivityTarif.java` and `activity_tarif.xml`  
Here users can select ticket prices (vary by cinema). The number of prices selected must match the number of seats. After that, the app navigates to: `InfosPersonnelles.java`.

![Image](https://github.com/user-attachments/assets/7d838643-c101-45f3-8922-8f145fbefc0d)

### `InfosPersonnelles.java` and `activity_infos_personnelles.xml`  
Users enter their information (name, surname, email), which is stored before proceeding to: `TicketResumeAll.java`.

![Image](https://github.com/user-attachments/assets/fa7a1551-f724-46ff-838e-1cac0e70abdc)

### `TicketResumeAll.java` and `activity_ticket_resume_all.xml`  
This page displays all ticket details from film choice to final confirmation. It also includes a QR Code to retrieve the ticket info. The user can then validate to go to the last page: `Remerciements.java`.

![Image](https://github.com/user-attachments/assets/d90a4647-2775-4d96-b766-2265b38aeb4f)

### `Remerciements.java` and `activity_remerciements.xml`  
This final page displays a thank-you message from the cinema and offers a return button to go back to the now-showing films page (`MainActivity.java`).

![Image](https://github.com/user-attachments/assets/0e4eb4c6-e741-429e-83ca-539653e83921)

### `DatabaseActivity.java` and `activity_database.xml`  
This is an activity not present in the production version. It was used for development purposes to manually populate the database. It can be accessed by uncommenting a line in the `onCreate` method of `MainActivity.java`.

### `DataMainActivity.java`  
This script gathers all data-retrieval requests from the database and enables:
- `MainActivity.java`: displaying cinema names and all related film info  
- `FilmDetail.java`: displaying full film info, showtimes, trailer, and poster  
- `ReservationPlaces.java`: displaying the seat map of the screening room  
- `ActivityTarif.java`: displaying ticket prices for the selected cinema

## Database  
To host our database, we used https://firebase.google.com/. Here's the structure:

```
/
├── Film
├── Acteur
├── Auteur
├── Cinema/
│   ├── Diffusion
│   ├── Salle/
│   │   └── Siege
│   └── Tarif
├── Ticket
└── TypeFilm
```

- "Film" refers to the films that can be shown in a cinema. It includes an actor (link to "Acteur"), an author (link to "Auteur"), a genre (link to "TypeFilm"), a title, release date, duration, poster name, video name, a rating, and a summary.

- "Acteur" references all actors who have performed in a film, consisting of a first and last name.

- "Auteur" references all authors who created a film, consisting of a first and last name.

- "TypeFilm" references all film genres, consisting only of a name.

- "Ticket" references all past reservations. Each includes an email, first name, last name, a screening (link to "Diffusion"), a price (link to "Tarif"), and a seat (link to "Siege").

- "Cinema" references all cinemas appearing in the app. Each includes a name, street number, street name, and city. Every "Cinema" has:
  - "Tarif": references ticket prices (child, senior, adult, student)
  - "Salle": references each cinema room, composed of a screening type (4K, IMAX, etc.) and seats "Siege" positioned by x/y coordinates on a 12x15 grid
  - "Diffusion": references all screenings at the cinema, including the film (link to "Film"), the room (link to "Salle"), and the date and time the film is shown
 
  🎬 [see the demo of the video(it will download it)](https://github.com/Codastic25/CinemaTicket/raw/main/WhatsApp%20Video%202025-05-12%20at%2022.54.12.mp4)

