import java.io.*;
import java.util.*;
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Location {
    private String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Movie {
    private String title;
    private String genre;
    private int duration;
    private List<String> cast;

    public Movie(String title, String genre, int duration, List<String> cast) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.cast = cast;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public List<String> getCast() {
        return cast;
    }

    public String getName() {
        return title;
    }
}

class Theater {
    private String name;
    private List<String> showTimings;
    private Map<String, boolean[][]> seats;

    public Theater(String name, List<String> showTimings, int numRows, int numCols) {
        this.name = name;
        this.showTimings = showTimings;
        this.seats = new HashMap<>();
        initializeSeats(numRows, numCols);
    }

    private void initializeSeats(int numRows, int numCols) {
        seats.put("Recliner", new boolean[numRows][numCols]);
        seats.put("Platinum", new boolean[numRows][numCols]);
        seats.put("Gold", new boolean[numRows][numCols]);
        seats.put("Silver", new boolean[numRows][numCols]);
        for (boolean[][] categorySeats : seats.values()) {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    // Simulate some seats as already booked
                    if (i < numRows / 2 && j < numCols / 2) {
                        categorySeats[i][j] = true;
                    } else {
                        categorySeats[i][j] = false;
                    }
                }
            }
        }
    }
    

    public String getName() {
        return name;
    }

    public List<String> getShowTimings() {
        return showTimings;
    }

    public Map<String, boolean[][]> getSeats() {
        return seats;
    }

    public void markSeat(String category, int row, int col) {
        seats.get(category)[row][col] = true;
    }
}

public class MovieTicketBooking{
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();
    private static List<Location> locations = new ArrayList<>();
    private static List<Movie> movies = new ArrayList<>();
    private static List<Theater> theaters = new ArrayList<>();
    private static final String USER_DATA_FILE = "users.txt";
    private static final String TICKET_DETAILS_FILE = "ticket_details.txt";

    public static void main(String[] args) {
        initializeData();
        loadUserData();
        System.out.println("Welcome to BookGehlot!");
        User currentUser = authenticateOrRegister();
        Location selectedLocation = selectLocation();
        Movie selectedMovie = selectMovie();
        Theater selectedTheater = selectTheater(selectedMovie);
        String selectedShowTiming = selectShowTiming(selectedTheater);
        //int numPeople = selectNumberOfPeople();
        String selectedCategory = selectSeatCategory();
        int numSeats = getIntInput("Enter the number of seats you want to book: ");
        selectSeats(selectedTheater.getSeats().get(selectedCategory), selectedCategory, numSeats);
        String paymentMethod = selectPaymentMethod();
        double paymentAmount = calculatePayment(selectedCategory, numSeats);
        boolean paymentSuccess = processPayment(paymentMethod, paymentAmount);
        if (paymentSuccess) {
            displayTicketDetails(selectedMovie, selectedTheater, selectedShowTiming, numSeats, paymentMethod, paymentAmount, selectedCategory);
           // boolean printTicket = askToPrintTicket();
           // if (printTicket) {
             //   writeTicketDetailsToFile(selectedMovie, selectedTheater, selectedShowTiming, numPeople, paymentMethod, paymentAmount, selectedCategory);
            //}
           // System.exit(0);
        }
    }
    private static void initializeData() {
        // Initialize users, locations, movies, theaters
        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));
        users.add(new User("user3", "password3"));

        locations.add(new Location("Mumbai"));
        locations.add(new Location("Delhi"));
        locations.add(new Location("Bangalore"));
        locations.add(new Location("Chennai"));
        locations.add(new Location("Kolkata"));
        locations.add(new Location("Hyderabad"));

        movies = new ArrayList<>();

        // Bollywood movies
        List<String> castDDLJ = new ArrayList<>();
        castDDLJ.add("Shah Rukh Khan");
        castDDLJ.add("Kajol");
        movies.add(new Movie("Dilwale Dulhania Le Jayenge", "Romance", 181, castDDLJ));
        
        List<String> castPK = new ArrayList<>();
        castPK.add("Aamir Khan");
        castPK.add("Anushka Sharma");
        movies.add(new Movie("PK", "Comedy/Drama", 153, castPK));
        
        List<String> castBB = new ArrayList<>();
        castBB.add("Salman Khan");
        castBB.add("Harshaali Malhotra");
        movies.add(new Movie("Bajrangi Bhaijaan", "Comedy/Drama", 159, castBB));
        
        // Hollywood movies
        List<String> castTitanic = new ArrayList<>();
        castTitanic.add("Leonardo DiCaprio");
        castTitanic.add("Kate Winslet");
        movies.add(new Movie("Titanic", "Romance/Drama", 195, castTitanic));
        
        List<String> castAvatar = new ArrayList<>();
        castAvatar.add("Sam Worthington");
        castAvatar.add("Zoe Saldana");
        movies.add(new Movie("Avatar", "Science Fiction/Adventure", 162, castAvatar));
        
        List<String> castInception = new ArrayList<>();
        castInception.add("Leonardo DiCaprio");
        castInception.add("Joseph Gordon-Levitt");
        movies.add(new Movie("Inception", "Science Fiction/Thriller", 148, castInception));
        
        // More Bollywood movies
        List<String> cast3Idiots = new ArrayList<>();
        cast3Idiots.add("Aamir Khan");
        cast3Idiots.add("Kareena Kapoor");
        movies.add(new Movie("3 Idiots", "Comedy/Drama", 170, cast3Idiots));
        
        List<String> castLagaan = new ArrayList<>();
        castLagaan.add("Aamir Khan");
        castLagaan.add("Gracy Singh");
        movies.add(new Movie("Lagaan", "Sports/Drama", 224, castLagaan));
        
        // More Hollywood movies
        List<String> castInterstellar = new ArrayList<>();
        castInterstellar.add("Matthew McConaughey");
        castInterstellar.add("Anne Hathaway");
        movies.add(new Movie("Interstellar", "Science Fiction/Drama", 169, castInterstellar));
        
        List<String> castTheShawshankRedemption = new ArrayList<>();
        castTheShawshankRedemption.add("Tim Robbins");
        castTheShawshankRedemption.add("Morgan Freeman");
        movies.add(new Movie("The Shawshank Redemption", "Drama", 142, castTheShawshankRedemption));
        
        List<String> castTheGodfather = new ArrayList<>();
        castTheGodfather.add("Marlon Brando");
        castTheGodfather.add("Al Pacino");
        movies.add(new Movie("The Godfather", "Crime/Drama", 175, castTheGodfather));
        
        
        
        
                // Theaters
                List<String> showTimings1 = new ArrayList<>(Arrays.asList("10:00 AM", "1:00 PM", "4:00 PM", "7:00 PM", "10:00 PM"));
                theaters.add(new Theater("PVR Cinemas", showTimings1, 10, 20));
        

                List<String> showTimings2 = new ArrayList<>(Arrays.asList("11:00 AM", "2:00 PM", "5:00 PM", "8:00 PM", "11:00 PM"));
                theaters.add(new Theater("INOX", showTimings2, 10, 20));
                // More theaters
                List<String> showTimings3 = new ArrayList<>(Arrays.asList("9:00 AM", "12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM"));
                theaters.add(new Theater("Cinepolis", showTimings3, 8, 18));
        
                List<String> showTimings4 = new ArrayList<>(Arrays.asList("10:30 AM", "1:30 PM", "4:30 PM", "7:30 PM", "10:30 PM"));
                theaters.add(new Theater("AMC Theatres", showTimings4, 12, 24));
        
                List<String> showTimings5 = new ArrayList<>(Arrays.asList("10:00 AM", "1:30 PM", "4:00 PM", "7:30 PM", "10:00 PM"));
                theaters.add(new Theater("Regal Cinemas", showTimings5, 15, 30));
        
                List<String> showTimings6 = new ArrayList<>(Arrays.asList("9:30 AM", "12:30 PM", "3:30 PM", "6:30 PM", "9:30 PM"));
                theaters.add(new Theater("Vue Cinemas", showTimings6, 10, 22));
        
                List<String> showTimings7 = new ArrayList<>(Arrays.asList("11:30 AM", "2:30 PM", "5:30 PM", "8:30 PM", "11:30 PM"));
                theaters.add(new Theater("Cinemark Theatres", showTimings7, 14, 28));
        
    }

    private static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String username = userData[0];
                String password = userData[1];
                users.add(new User(username, password));
            }
            System.out.println("User data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading user data from file: " + e.getMessage());
        }
    }

    private static void writeUserDataToFile(User newUser) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(newUser.getUsername() + "," + newUser.getPassword() + "\n");
            System.out.println("New user registered and data written to file: " + USER_DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error writing user data to file: " + e.getMessage());
        }
    }

    private static User authenticateOrRegister() {
        System.out.println("Do you have an existing account? (yes/no)");
        String choice = scanner.nextLine().trim();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.println("Enter password: ");
            String password = scanner.nextLine().trim();
            int attempts = 0;
            while (!isValidUser(username, password) && attempts < 3) {
                System.out.println("Incorrect username or password. Please try again.");
                System.out.println("Enter username: ");
       
                System.out.println("Enter password: ");
                password = scanner.nextLine().trim();
                attempts++;
            }
            if (isValidUser(username, password)) {
                System.out.println("Login successful!");
                return new User(username, password);
            } else {
                System.out.println("Reset password link is sent to your email.");
                // Logic for sending reset password link to email
                // After reset, make the user login again
                return authenticateOrRegister();
            }
        } else if (choice.equalsIgnoreCase("no")) {
            return registerNewUser();
        } else {
            System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
            return authenticateOrRegister();
        }
    }

    private static boolean isValidUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static User registerNewUser() {
        System.out.println("Enter a new username: ");
        String newUsername = scanner.nextLine().trim();
        System.out.println("Enter a new password: ");
        String newPassword = scanner.nextLine().trim();
        User newUser = new User(newUsername, newPassword);
        users.add(newUser);
        writeUserDataToFile(newUser);
        return newUser;
    }

    private static Location selectLocation() {
        System.out.println("Select your location:");
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i + 1) + ". " + locations.get(i).getName());
        }
        int choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice < 1 || choice > locations.size()) {
            System.out.println("Invalid choice. Please select a valid location.");
            return selectLocation();
        }
        return locations.get(choice - 1);
    }

    private static Movie selectMovie() {
        System.out.println("Select a movie:");
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            System.out.println((i + 1) + ". " + movie.getTitle() + " (" + movie.getGenre() + ", " + movie.getDuration() + " mins)");
        }
        int choice = getIntInput("Enter the number of your choice: ");
        if (choice < 1 || choice > movies.size()) {
            System.out.println("Invalid choice. Please select a valid movie.");
            return selectMovie();
        }
        return movies.get(choice - 1);
    }
    
    private static Theater selectTheater(Movie selectedMovie) {
        System.out.println("Theaters screening " + selectedMovie.getTitle() + ":");
        for (int i = 0; i < theaters.size(); i++) {
            Theater theater = theaters.get(i);
            System.out.println((i + 1) + ". " + theater.getName());
        }
        int choice = getIntInput("Enter the number of your choice: ");
        if (choice < 1 || choice > theaters.size()) {
            System.out.println("Invalid choice. Please select a valid theater.");
            return selectTheater(selectedMovie);
        }
        return theaters.get(choice - 1);
    }
    
    private static String selectShowTiming(Theater selectedTheater) {
        System.out.println("Select a show timing:");
        List<String> showTimings = selectedTheater.getShowTimings();
        for (int i = 0; i < showTimings.size(); i++) {
            System.out.println((i + 1) + ". " + showTimings.get(i));
        }
        int choice = getIntInput("Enter the number of your choice: ");
        if (choice < 1 || choice > showTimings.size()) {
            System.out.println("Invalid choice. Please select a valid show timing.");
            return selectShowTiming(selectedTheater);
        }
        return showTimings.get(choice - 1);
    }
    
    //private static int selectNumberOfPeople() {
      //  int numPeople = getIntInput("Enter the number of people (1-4): ");
        //if (numPeople < 1 || numPeople > 4) {
          //  System.out.println("Invalid input. Number of people must be between 1 and 4.");
            //return selectNumberOfPeople();
       // }
        //return numPeople;
    //}
    
    private static String selectSeatCategory() {
        System.out.println("Select a seat category:");
        System.out.println("1. Recliner (Price: $20)");
        System.out.println("2. Platinum (Price: $15)");
        System.out.println("3. Gold (Price: $10)");
        System.out.println("4. Silver (Price: $5)");
        int choice = getIntInput("Enter the number of your choice: ");
        switch (choice) {
            case 1:
                return "Recliner";
            case 2:
                return "Platinum";
            case 3:
                return "Gold";
            case 4:
                return "Silver";
            default:
                System.out.println("Invalid choice. Please select a valid seat category.");
                return selectSeatCategory();
        }
    }
    private static void displaySeats(boolean[][] seats) {
        System.out.println("Seat Matrix:");
        System.out.print("  ");
        for (int i = 0; i < seats[0].length; i++) {
            System.out.print((char)('A' + i) + " ");
        }
        System.out.println();
        for (int i = 0; i < seats.length; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print((seats[i][j] ? "X" : "O") + " ");
            }
            System.out.println();
        }
        System.out.println("------------------Screen-----------------");
    }
    
    
    
    
    // Inside the selectSeats method
    private static void selectSeats(boolean[][] seats, String category, int numSeats) {
        Scanner scanner = new Scanner(System.in);
    
        // Initialize all seats as available
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = false; // Available seat
            }
        }
    
        // Simulate maximum number of seats already booked
        int maxSeatsToBook = (int) (seats.length * seats[0].length * 0.98); // Assuming 70% of seats are booked
        int bookedSeats = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (bookedSeats < maxSeatsToBook) {
                    seats[i][j] = true; // Booked seat
                    bookedSeats++;
                } else {
                    break;
                }
            }
            if (bookedSeats >= maxSeatsToBook) {
                break;
            }
        }
    
        displaySeats(seats);
        int availableSeats = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (!seats[i][j]) { // Seat is available
                    availableSeats++;
            }
        }
    }
    while(numSeats > (seats.length * seats[0].length) - maxSeatsToBook)
    {
        if (numSeats > (seats.length * seats[0].length) - maxSeatsToBook) {
            System.out.println("There are not enough available seats. Select from the available seats or quit booking.");
            System.out.println("Available seats: " + availableSeats);
            System.out.println("Do you want to select from available seats? (yes/no)");
            String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("yes")) {
            numSeats = getIntInput("Enter the number of seats you want to book: ");
        } else {
            System.out.println("Booking canceled.");
            System.exit(0);
            return;
        }
    }
}
       for (int count = 0; count < numSeats; count++) {
    while (true) {
        System.out.print("Enter the row and column letter of the seat you want to book (e.g., A A or A B): ");
        String input = scanner.nextLine().trim();

        // Split input into row and column parts
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            System.out.println("Invalid input! Please enter row and column letters separated by space.");
            continue;
        }

        char row = parts[0].charAt(0);
        char colLetter = parts[1].charAt(0);

        // Validate row input
        if (!(row >= 'A' && row <= 'Z')) {
            System.out.println("Invalid row! Please enter a valid uppercase letter (A-Z) for the row.");
            continue;
        }

        // Convert row to index (A -> 0, B -> 1, ...)
        int rowIndex = row - 'A';

        // Validate column input
        int colIndex = colLetter - 'A'; // Convert column letter to index (A -> 0, B -> 1, ...)

        // Check if the selected seat is within bounds
        if (rowIndex < 0 || rowIndex >= seats.length || colIndex < 0 || colIndex >= seats[0].length) {
            System.out.println("Invalid seat! Please select a valid row and column.");
            continue;
        }

        // Check if the selected seat is available
        if (seats[rowIndex][colIndex]) {
            System.out.println("Seat already booked! Please select another seat.");
        } else {
            // Mark seat as booked
            seats[rowIndex][colIndex] = true;
            System.out.println("Seat booked successfully!");
            displaySeats(seats);
            break; // Exit the loop after booking the seat
        }
    }
}

    
    
}

    

    
    
    
    
    
    
    
    private static String selectPaymentMethod() {
        System.out.println("Select your payment method:");
        System.out.println("1. GPay");
        System.out.println("2. Debit Card");
        System.out.println("3. Cash");
        System.out.println("4. LazyPay");
        int choice = getIntInput("Enter the number of your payment method: ");
        switch (choice) {
            case 1:
                return "GPay";
            case 2:
                return "Debit Card";
            case 3:
                return "Cash";
            case 4:
                return "LazyPay";
            default:
                System.out.println("Invalid choice. Please select a valid payment method.");
                return selectPaymentMethod();
        }
    }
    
    private static double calculatePayment(String category, int numPeople) {
        // Implement payment calculation logic
        // Calculate total payment based on seat category and number of people
        double pricePerSeat;
        switch (category) {
            case "Recliner":
                pricePerSeat = 20.0;
                break;
            case "Platinum":
                pricePerSeat = 15.0;
                break;
            case "Gold":
                pricePerSeat = 10.0;
                break;
            case "Silver":
                pricePerSeat = 5.0;
                break;
            default:
                pricePerSeat = 0.0;
        }
        return pricePerSeat * numPeople;
    }
    
    private static boolean processPayment(String paymentMethod, double paymentAmount) {
        // Implement payment processing logic
        // Process payment using the selected payment method
        // Return true if payment is successful, false otherwise
        System.out.println("Processing payment of $" + paymentAmount + " via " + paymentMethod + "...");
        // Simulate payment processing (always return true for now)
        return true;
    }
    
    private static void displayTicketDetails(Movie selectedMovie, Theater selectedTheater, String selectedShowTiming, int numPeople, String paymentMethod, double paymentAmount, String selectedCategory) {
        // Display ticket details including movie, theater, show timing, number of people, payment method, and payment amount
        System.out.println("--------------------------------------------------");
        System.out.println("                   BookGehlot                     ");
        System.out.println("--------------------------------------------------");
        System.out.println("Ticket Details:");
        System.out.println("Movie: " + selectedMovie.getName());
        System.out.println("Theater: " + selectedTheater.getName());
        System.out.println("Show Timing: " + selectedShowTiming);
        System.out.println("Number of People: " + numPeople);
        System.out.println("Seat Category: " + selectedCategory);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment Amount: $" + paymentAmount);
        System.out.println("--------------------------------------------------");
    
        // Prompt the user to print the ticket
        System.out.println("Do you want to print the ticket? (yes/no)");
    
        // Read user input
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toLowerCase();
    
        // Check if the user wants to print the ticket
        if (input.equals("yes")) {
            writeTicketDetailsToFile(selectedMovie, selectedTheater, selectedShowTiming, numPeople, paymentMethod, paymentAmount, selectedCategory);
            System.out.println("Ticket printed successfully!");
        } else {
            System.out.println("Ticket not printed.");
        }
    }
    
    
    
    
    
    private static void writeTicketDetailsToFile(Movie selectedMovie, Theater selectedTheater, String selectedShowTiming, int numPeople, String paymentMethod, double paymentAmount, String selectedCategory) {
        // Implement logic to write ticket details to a file
        // Write ticket details to TICKET_DETAILS_FILE
        String ticketDetails = "Movie: " + selectedMovie.getTitle() + "\n" +
                               "Theater: " + selectedTheater.getName() + "\n" +
                               "Show Timing: " + selectedShowTiming + "\n" +
                               "Number of People: " + numPeople + "\n" +
                               "Seat Category: " + selectedCategory + "\n" +
                               "Payment Method: " + paymentMethod + "\n" +
                               "Payment Amount: $" + paymentAmount + "\n";
        try {
            FileWriter writer = new FileWriter("ticket_details.txt");
            writer.write(ticketDetails);
            writer.close();
            System.out.println("Ticket details written to file: ticket_details.txt");
        } catch (IOException e) {
            System.out.println("Error writing ticket details to file: " + e.getMessage());
        }
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print(prompt);
            scanner.next(); // Clear the invalid input
        }
        return scanner.nextInt();
    }

}