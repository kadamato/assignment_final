import javax.annotation.processing.SupportedOptions;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.function.DoubleToIntFunction;
import java.util.regex.*;

public class Main {


     public static float formatPrice (String price) {
         float result = -1;

         String priceRegex = "\\d+";
         Pattern pattern = Pattern.compile(priceRegex);
         Matcher matcher = pattern.matcher(price);


         if(matcher.matches()) {
             float floatValue = Float.parseFloat(price);
             int decimalPlaces = 2;

             String formattedValue = String.format("%." + decimalPlaces + "f", floatValue);

             Float parsed = Float.parseFloat(formattedValue);
             result =  parsed;
         }

         return result;



     }
    public static boolean formatIsbnCode (String isbnCode ) {
        String regex = "\\d{3}-\\d{10}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(isbnCode);

        if(matcher.matches()) return true;
        return  false;
    }
    public static boolean formatPublicationYear (String publicationYear) {

        String datePattern = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(publicationYear);

        if(matcher.matches()) {
            return true;
        }
        return false;

    }
    public  static  boolean formatPageNumber (int pageNumber) {
         boolean result = false;
         String convertToString = Integer.toString(pageNumber);

         if(convertToString.startsWith("0")) {
             result = false;
         }
         else {
             result = true;
         }
         return result;

    }

    public static boolean formatEmail (String email) {

        String emailRegex = "[a-zA-Z0-9]+@gmail\\.com";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) return true;
        return false;
    }
    public static boolean formatPublisher (String publisherName) {

        // Define the regex pattern
        String regex = "[a-zA-Z]+";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher with the input string
        Matcher matcher = pattern.matcher(publisherName);

        if(matcher.matches()) return true;
        return false;
    }

    public static boolean formatAlphaNumber (String text) {
        String alphanumericRegex = "[a-zA-Z0-9\\s\\-\\.\\(\\)]+";

        Pattern pattern = Pattern.compile(alphanumericRegex);

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) return true;
        return false;
    }

    public static boolean formatPhoneNumber (String phoneNumber) {
        String phoneNumberRegex = "\\d{10}";

        Pattern pattern = Pattern.compile(phoneNumberRegex);

        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) return true;
        return false;
    }

    public static boolean limitDescription (String  description) {
         String[] countWords = description.split(" ");
         int count = 0;
         for (String word: countWords) {
             count += 1;
         }
         if(count < 500) return  true;
         return false;
    }

    public static boolean formatString (String text) {
        String lettersRegex = "[a-zA-Z]+";

        Pattern pattern = Pattern.compile(lettersRegex);

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) return true;
        return false;
    }
    public static boolean  backOne () {

         Scanner  scanner =  new Scanner(System.in);
        System.out.println("\n---------------select back or continue----------------");
        System.out.println("0. Back");
        System.out.println("1. Continue");
        System.out.print(">User ");

        boolean result = false;

        try {
            int back = scanner.nextInt();
            scanner.nextLine();

            if(back == 0) return  result =  true;
            else if (back == 1) {
                result = false;
            }else {
                logErrorSyntax();
                result = true;
            }
        }
        catch (InputMismatchException err) {
            logErrorSyntax();
            // clear buffer
            clearBufferMemory(scanner);
            result = true;
        }
        finally {
             return result;
        }


    }

    public static void clearBufferMemory (Scanner scanner) {
         scanner.nextLine();
    }

    public static void  showListFeatureForUser () {

        System.out.println("-------------------Mode store-------------------\n");
        System.out.println("To interact with the store, use the command below");

        System.out.println("1. Show all books");
        System.out.println("2. Find Book follow title");
        System.out.println("3. Sort book follow author name");
        System.out.println("4. Sort book follow price");
        System.out.println("5. Filter book follow author name");
        System.out.println("6. Filter book follow genre");
        System.out.println("0. Back");
    }

    public static void  logErrorSyntax () {
        System.out.println(Support.redColor("Command is wrong!, only select available options"));
    }

    public static  boolean checkEmpty (String field) {
        if(field.trim().isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }


    public static void main (String[] args)  {

                BookStore  amazonBook =  new BookStore("Amazon book", "2023-11-02");

                if(!amazonBook.connectDB()) {
                    System.out.println("connect failed");
                }
                else {
                    // pre-get

                    amazonBook.getBooks();
                    amazonBook.getGenres();
                    amazonBook.getAuhors();
                    amazonBook.getNationalities();
                    amazonBook.getPublishers();

                    boolean menu = true;
                    Scanner scanner = new Scanner(System.in);

                    // loop1
                    while (menu) {
                        System.out.println("Choose your role");
                        System.out.println("1. User");
                        System.out.println("2. Admin");
                        try {
                            System.out.print(">");
                            int isWho = scanner.nextInt();
                            if(isWho == 1) {
                                // loop store
                                boolean userRun = true;
                                while(userRun) {
                                    System.out.println("-------------------Welcome to book store-------------------\n");
                                    System.out.println("Select options as shown");
                                    System.out.println("1. Join store");
                                    System.out.println("2. Show info of store");
                                    System.out.println("0. Back");
                                    System.out.print(">User ");
                                    try {
                                        int userCommand =  scanner.nextInt();
                                        if (userCommand ==  1) {
                                            boolean runCommand = true;
                                            while(runCommand) {
                                                showListFeatureForUser();
                                                System.out.print(">User ");
                                                try {
                                                    int command = scanner.nextInt();
                                                    scanner.nextLine();
                                                    if(command == 1) {
                                                       boolean runCommandOne =  true;
                                                       while (runCommandOne) {
                                                           amazonBook.showBooks();
                                                           if(backOne()) break;

                                                       }
                                                    }
                                                    else if(command  == 5) {
                                                       boolean runCommandFive = true;
                                                       while  (runCommandFive) {
                                                           System.out.println("Enter author name to find books");
                                                           System.out.print(">User ");
                                                           String authorName = scanner.nextLine();

                                                           amazonBook.filterAuthorName(authorName);
                                                           if(Main.backOne()) break;
                                                       }
                                                    }
                                                    else if (command == 2) {
                                                       boolean runCommandTwo = true;
                                                       while  (runCommandTwo) {
                                                           System.out.print("Enter book name to find book:");
                                                           String bookName = scanner.nextLine();
                                                           amazonBook.findBook(bookName);
                                                           if(Main.backOne()) break;
                                                       }
                                                    }
                                                    else if(command ==  6) {
                                                       boolean runCommandSix = true;
                                                       while  (runCommandSix) {
                                                          System.out.println("Enter genre of book to find book ");
                                                          System.out.print(">User ");
                                                          String genre = scanner.nextLine();

                                                          amazonBook.filterFollowGenre(genre);

                                                          if(Main.backOne()) break;
                                                       }

                                                    }
                                                    else if(command == 3) {
                                                       boolean runCommandThree = true;
                                                       while  (runCommandThree) {
                                                          System.out.println("1. Sort author name from a-z (low - high)");
                                                          System.out.print(">User ");
                                                          try {
                                                              int sortAuthorCommand = scanner.nextInt();

                                                              if(sortAuthorCommand == 1) {
                                                                  amazonBook.sortAuthor("a-z");
                                                                  if(Main.backOne()) break;
                                                              }
                                                              else {
                                                                  Main.logErrorSyntax();
                                                              }
                                                          }
                                                          catch (InputMismatchException err) {
                                                              Main.logErrorSyntax();
                                                              // clear buffer
                                                              clearBufferMemory(scanner);
                                                          }


                                                       }
                                                    }
                                                    else if (command == 4) {
                                                                boolean runSortAuthor = true;
                                                                while  (runSortAuthor) {
                                                                    try {
                                                                        System.out.println("1. Sort price of book from a-z(high - low)");
                                                                        System.out.print(">User ");
                                                                        int commandPrice  = scanner.nextInt();

                                                                        if(commandPrice == 1) {
                                                                            amazonBook.sortPrice("a-z");
                                                                            if(Main.backOne()) break;
                                                                        }
                                                                        else {
                                                                            Main.logErrorSyntax();
                                                                        }
                                                                    }
                                                                    catch (InputMismatchException error) {
                                                                        Main.logErrorSyntax();
                                                                        // clear buffer
                                                                        clearBufferMemory(scanner);
                                                                    }

                                                                }
                                                            }
                                                    else if (command == 0) {
                                                                break;
                                                            }
                                                    else {
                                                        Main.logErrorSyntax();
                                                    }
                                                }
                                                catch (InputMismatchException  error) {
                                                    Main.logErrorSyntax();
                                                    // clear buffer
                                                    clearBufferMemory(scanner);
                                                }
                                            }
                                        }
                                        else if (userCommand == 2) {
                                            System.out.println("----------------Info store--------------");
                                            System.out.println(amazonBook + "\n");

                                            System.out.println("0. Back");
                                            System.out.print(">User ");
                                            try {
                                                int back = scanner.nextInt();
                                                if(back == 0) continue;
                                                logErrorSyntax();
                                            }
                                            catch (InputMismatchException err) {
                                                logErrorSyntax();
                                                clearBufferMemory(scanner);
                                            }
                                        }
                                        else if (userCommand == 0) {
                                           break;
                                        }
                                        else {
                                            logErrorSyntax();
                                            clearBufferMemory(scanner);
                                        }
                                    }
                                    catch (InputMismatchException err){
                                        logErrorSyntax();
                                        // clear buffer
                                        clearBufferMemory(scanner);
                                    }
                                }
                            }
                                // menu for admin
                            else if (isWho == 2){
                                    boolean run = true;
                                    while (run) {
                                        System.out.println("-------------------This is admin mode-------------------\n");
                                        System.out.println("1. Add book");
                                        System.out.println("2. Update book");
                                        System.out.println("3. Delete book");
                                        System.out.println("0. Back");
                                        System.out.print(">Enter option: ");

                                        try {
                                            int option = scanner.nextInt();
                                            scanner.nextLine();

                                            String title = "", genre = "", authorName = "" , authorEmail ="", authorNationality ="" , authorPhone ="" , authorDescription="", isbnCode=""  , publisher ="" , pubYear = "";
                                            int pageNumber = 0;
                                            int bookQuantity = 0;
                                            float sellingPrice = 0 ;

                                            if(option == 1) {
                                                System.out.println("------------------Add book--------------");


                                                boolean titleRun = true;
                                                while (titleRun) {
                                                    System.out.println("Enter the book's information\n");
                                                    System.out.print("Title(required): ");
                                                    String bookT  = scanner.nextLine();



                                                    if(checkEmpty(bookT)) {
                                                        System.out.println(Support.redColor("The field cannot be left blank"));
                                                    }
                                                    else if(!formatAlphaNumber(bookT)) {
                                                        System.out.println(Support.redColor("Field only can enter letters and numbers"));
                                                    }
                                                    else if(amazonBook.checkBook(bookT)) {
                                                        System.out.println(Support.redColor("Book is exist"));
                                                    }
                                                    else {
                                                        title = bookT;
                                                        System.out.println(Support.greenColor("Add title for book success"));
                                                        break;
                                                    }
                                                }

                                                boolean genreRun =  true;
                                                while (genreRun) {
                                                    System.out.println("Choose a genre for your book");
                                                    System.out.println("1. Choose from available genres");
                                                    System.out.println("2. Add a new genre");
                                                    System.out.print("Enter genre: ");

                                                    try {
                                                        int genreFeatureOptions = scanner.nextInt();
                                                        scanner.nextLine();
                                                        if(genreFeatureOptions == 1) {
                                                            boolean  genreOptionOneRun = true;
                                                            while (genreOptionOneRun) {
                                                                if(amazonBook.genresList.isEmpty()) {
                                                                    System.out.println("No any genres");
                                                                    System.out.println("Do you want back and add a new genre?(Enter OK)");
                                                                    String reply = scanner.nextLine();
                                                                    if(reply.equals("OK")) {
                                                                        break;
                                                                    }
                                                                }
                                                                else {
                                                                    System.out.println("All genres of books are available");
                                                                    amazonBook.showGenres();
                                                                    System.out.print("Enter genre: ");
                                                                    try {
                                                                        int genreOptions = scanner.nextInt();
                                                                        scanner.nextLine();

                                                                        String isExistGenre =  amazonBook.findGenre(genreOptions);
                                                                        if(isExistGenre.isEmpty()) {
                                                                            System.out.println(Support.redColor("The field cannot be left blank"));
                                                                        }
                                                                        else {
                                                                            genre = Support.capitalizeText(isExistGenre);
                                                                            System.out.println(Support.greenColor("Add " + genre + " genre for book success"));
                                                                            genreRun = false;
                                                                            break;
                                                                        }
                                                                    }
                                                                    catch (InputMismatchException err) {
                                                                        logErrorSyntax();
                                                                        clearBufferMemory(scanner);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        else if (genreFeatureOptions == 2) {
                                                            System.out.println("Enter the book genre\n");

                                                            boolean genreOptionTwoRun = true;
                                                            while (genreOptionTwoRun) {
                                                                System.out.print("Genre(required): ");
                                                                String newGenre =  scanner.nextLine();

                                                                if(checkEmpty(newGenre)) {
                                                                    System.out.println(Support.redColor("The field cannot be left blank"));
                                                                }
                                                                else if(amazonBook.checkGenre(newGenre)) {
                                                                    System.out.println(Support.redColor("Genre is exist"));
                                                                }
                                                                else {
                                                                    genre = Support.capitalizeText(newGenre);
                                                                    System.out.println(Support.greenColor("Add " + genre + " genre for book success"));
                                                                    genreRun = false;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        else {
                                                            logErrorSyntax();
                                                        }
                                                    }
                                                    catch (InputMismatchException err) {
                                                        logErrorSyntax();
                                                        clearBufferMemory(scanner);
                                                    }
                                                }

                                                boolean quantityRun = true;
                                                while (quantityRun) {
                                                    try {
                                                        System.out.print("Quantity(required): ");
                                                        String quantity =  scanner.nextLine();

                                                        int convertQuantity = Integer.parseInt(quantity);


                                                        if(checkEmpty(quantity)) {
                                                            System.out.println(Support.redColor("The field cannot be left blank"));
                                                        }
                                                        else if(convertQuantity < 1) {
                                                            System.out.println(Support.redColor("Quantity must grater than 0"));
                                                        }
                                                        else {
                                                            bookQuantity = convertQuantity;
                                                            System.out.println(Support.greenColor("Added quantity for book success"));
                                                            break;
                                                        }
                                                    }
                                                    catch (NumberFormatException error) {
                                                        System.out.println(Support.redColor("Only can enter number"));
                                                    }
                                                }

                                                boolean priceRun = true;
                                                while (priceRun) {
                                                    try {
                                                        System.out.print("Selling price(required): ");
                                                        String price =  scanner.nextLine();
                                                        float checkAndConvert = formatPrice(price);

                                                        if(checkEmpty(price)) {
                                                            System.out.println(Support.redColor("The field cannot be left blank"));
                                                        }
                                                        else if (checkAndConvert == 0) {
                                                            System.out.println(Support.redColor("Price must be greater than 0 and only can be float numbers"));
                                                        }
                                                        else if(checkAndConvert != -1) {
                                                            System.out.println(Support.greenColor("Add price for book success"));
                                                            break;
                                                        }
                                                        else {
                                                            System.out.println(Support.redColor("Only can enter number"));
                                                        }
                                                    }
                                                    catch (InputMismatchException error) {
                                                        logErrorSyntax();
                                                        clearBufferMemory(scanner);
                                                    }
                                                }


                                                boolean authorRun =  true;
                                                while (authorRun) {
                                                    System.out.println("Add an author name to your book");
                                                    System.out.println("1. Select available authors");
                                                    System.out.println("2. Add new a author");

                                                    try {
                                                        System.out.print("Enter option: ");
                                                        int authorFeatureOptions =  scanner.nextInt();
                                                        scanner.nextLine();


                                                        if(authorFeatureOptions == 1) {
                                                            boolean authorOptionOneRun = true;
                                                            while (authorOptionOneRun) {
                                                                if(amazonBook.authorsList.isEmpty()) {
                                                                    System.out.println("No any authors");

                                                                    System.out.println("Do you want back and add a new author?(Enter OK)");
                                                                    String reply = scanner.nextLine();
                                                                    if(reply.equals("OK")) {
                                                                        break;
                                                                    }
                                                                }
                                                                else {
                                                                    System.out.println("All available authors");

                                                                    amazonBook.showAuthors();
                                                                    System.out.print("Choose author(enter number): ");

                                                                    try {
                                                                        int authorOption = scanner.nextInt();

                                                                        String name  = amazonBook.findAuthors(authorOption);

                                                                        if(name.isEmpty())  {
                                                                            System.out.println(Support.redColor("The field cannot be left blank"));
                                                                        }else {
                                                                            authorName = Support.capitalizeText(name);
                                                                            System.out.println(Support.greenColor("Added " +authorName + " as author"));
                                                                            authorRun = false;
                                                                            break;
                                                                        }
                                                                    }
                                                                    catch (InputMismatchException error) {
                                                                        logErrorSyntax();
                                                                        clearBufferMemory(scanner);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        else if (authorFeatureOptions == 2) {
                                                            boolean authorOptionTwoRun = true;
                                                            while (authorOptionTwoRun) {
                                                                System.out.println("Enter the info of the author ");
                                                                        //1. add author name
                                                                        boolean authorNameRun = true;
                                                                        while (authorNameRun) {
                                                                            System.out.print("Enter author name(required): ");
                                                                            String authorNameIn =  scanner.nextLine();

                                                                            boolean validateAuthorName = formatAlphaNumber(authorNameIn);

                                                                            if(checkEmpty(authorNameIn)) {
                                                                                System.out.println(Support.redColor("The field cannot be left blank"));
                                                                            }
                                                                            else if(!validateAuthorName) {
                                                                                System.out.println(Support.redColor("Field only can enter letters and numbers"));
                                                                            }
                                                                            else if(!amazonBook.checkAuthor(authorNameIn)) {
                                                                                 authorName = authorNameIn;
                                                                                 System.out.println(Support.greenColor("Added " + authorName + " as author success"));
                                                                                 break;
                                                                            }
                                                                            else {
                                                                                System.out.println(Support.redColor("Author is exist"));
                                                                            }
                                                                        }

                                                                        //2. Add author email
                                                                        boolean authorEmailRun = true;
                                                                        while (authorEmailRun) {
                                                                            System.out.print("Enter author email(optional): ");
                                                                            String authorEmailIn =  scanner.nextLine();

                                                                            boolean validateAuthorEmail = formatEmail(authorEmailIn);

                                                                            if(checkEmpty(authorEmailIn)) {
                                                                                System.out.println(Support.greenColor("You have left this field blank"));
                                                                                break;
                                                                            }
                                                                            else if (!validateAuthorEmail) {
                                                                                System.out.println(Support.redColor("You must enter the correct email format emailname@gmai.com"));
                                                                            }
                                                                            else if(!amazonBook.checkAuthor(authorName, authorEmailIn)) {
                                                                                authorEmail = authorEmailIn;
                                                                                System.out.println(Support.greenColor("Added " + authorEmail + " as email of author success"));
                                                                                break;
                                                                            }
                                                                            else {
                                                                                System.out.println(Support.redColor("Author is exist"));
                                                                            }
                                                                        }
                                                                        //3. add author phone
                                                                        boolean authorPhoneRun = true;
                                                                        while (authorPhoneRun) {
                                                                            System.out.print("Enter author phone(optional): ");
                                                                            String authorPhoneIn =  scanner.nextLine();

                                                                            boolean validatePhoneAuthor = formatPhoneNumber(authorPhoneIn);


                                                                            if(checkEmpty(authorPhoneIn)) {
                                                                                System.out.println(Support.greenColor("You have left this field blank"));
                                                                                break;
                                                                            }
                                                                            else if(!validatePhoneAuthor) {
                                                                                System.out.println(Support.redColor("Field only can enter numbers  and must be 10 numbers "));
                                                                            }
                                                                           else if(!amazonBook.checkAuthor(authorName, authorEmail, authorPhoneIn)) {
                                                                                authorPhone = authorPhoneIn;
                                                                                System.out.println(Support.greenColor("Added " + authorPhone + " as phone number of author success"));
                                                                                break;
                                                                            }
                                                                            else {
                                                                                System.out.println(Support.redColor("Author is exist"));
                                                                            }
                                                                        }

                                                                        // 4. author nationality
                                                                        boolean authorNationalityRun = true;
                                                                        System.out.println("Select your country");
                                                                        amazonBook.showNationalities();
                                                                        while (authorNationalityRun) {
                                                                                System.out.print("Enter nationality(optional): ");
                                                                                String countryStt =  scanner.nextLine();

                                                                                if(formatString(countryStt)) {
                                                                                    System.out.println(Support.greenColor("You have left this field blank"));
                                                                                    authorNationality = "None";
                                                                                    break;
                                                                                }
                                                                                else {
                                                                                    try {
                                                                                        int stt = Integer.parseInt(countryStt);
                                                                                        String nationalityName = amazonBook.findNationalities(stt);

                                                                                        if(nationalityName.isEmpty()) {
                                                                                            System.out.println(Support.redColor("Command is wrong!, try again"));
                                                                                        }
                                                                                        else {
                                                                                            authorNationality = Support.capitalizeText(nationalityName);
                                                                                            System.out.println(Support.greenColor("Add " + nationalityName + " country " + "success"));
                                                                                            break;
                                                                                        }
                                                                                    }
                                                                                    catch (NumberFormatException error) {
                                                                                        System.out.println(Support.greenColor("You have left this field blank"));
                                                                                        break;
                                                                                    }
                                                                                }

                                                                        }

                                                                        // 5. add description
                                                                        boolean authorDescriptionRun = true;
                                                                        while (authorDescriptionRun) {
                                                                            System.out.print("Enter author description(optional): ");
                                                                            String authorDescriptionIn =  scanner.nextLine();


                                                                            boolean limitDescription= limitDescription(authorDescriptionIn);

                                                                            if(checkEmpty(authorDescriptionIn)) {
                                                                                System.out.println(Support.greenColor("You have left this field blank"));
                                                                                break;
                                                                            }
                                                                            else if(!limitDescription) {
                                                                                System.out.println(Support.redColor("Description limited to 500 words"));
                                                                            }
                                                                            else if(!amazonBook.checkAuthor(authorName, authorEmail, authorPhone, authorNationality, authorDescriptionIn)) {
                                                                                authorDescription = authorDescriptionIn;
                                                                                System.out.println(Support.greenColor("Added " + authorDescription + " as description of book success"));
                                                                                authorRun = false;
                                                                                break;
                                                                            }
                                                                            else {
                                                                                System.out.println(Support.redColor("Author is exist"));
                                                                            }
                                                                        }
                                                                        break;
                                                            }
                                                            break;
                                                        }
                                                        else {
                                                            logErrorSyntax();
                                                        }
                                                    }
                                                    catch (InputMismatchException error) {
                                                        logErrorSyntax();
                                                        clearBufferMemory(scanner);
                                                    }
                                                }

                                                boolean pageNumberRun = true;
                                                while (pageNumberRun) {
                                                    try {
                                                        System.out.print("Page number(number):");
                                                        int pNumber  = scanner.nextInt();
                                                        scanner.nextLine();

                                                        if(formatPageNumber(pNumber)) {
                                                            pageNumber = pNumber;
                                                            System.out.println(Support.greenColor("Add page number for book success"));
                                                            break;
                                                        }
                                                        else {
                                                            System.out.println(Support.redColor("Page number cannot start by 0 number, try again"));
                                                        }
                                                    }
                                                    catch (InputMismatchException error) {
                                                        logErrorSyntax();
                                                        clearBufferMemory(scanner);
                                                    }
                                                }

                                                boolean isbnCodeRun = true;
                                                while (isbnCodeRun) {
                                                    System.out.print("ISBN code: ");
                                                    isbnCode  =  scanner.nextLine();

                                                    if(!formatIsbnCode(isbnCode)) {
                                                        System.out.println(Support.redColor("ISBN code only can contain 14 digits follow pattern : ***-**********"));
                                                    }
                                                    else {
                                                        System.out.println(Support.greenColor("Add ISBN code for book success"));
                                                        break;
                                                    }
                                                }


                                                boolean pubYearRun = true;
                                                while (pubYearRun) {
                                                    System.out.print("Publication year(yyyy-mm-dd): ");
                                                    pubYear =  scanner.nextLine();

                                                    if(!formatPublicationYear(pubYear)) {
                                                        System.out.println(Support.redColor("Publication year must follow pattern yyyy-mm-dd"));
                                                    }
                                                    else {
                                                        System.out.println(Support.greenColor("Add publication year success"));
                                                        break;
                                                    }
                                                }

                                                boolean publisherRun  = true;
                                                while (publisherRun) {
                                                    System.out.println("Add publisher for your book");
                                                    System.out.println("1. Choose available publishers");
                                                    System.out.println("2. Add a new publisher");

                                                    try {
                                                        System.out.print("Enter option: ");
                                                        int publisherOption =  scanner.nextInt();
                                                        scanner.nextLine();


                                                        if(publisherOption == 1) {
                                                            boolean publisherOptionRun = true;
                                                            while (publisherOptionRun) {
                                                                if(amazonBook.publishersList.isEmpty()) {
                                                                    System.out.println("No any publishers");

                                                                    System.out.println("Do you want back and add a new  publisher?(Enter OK)");
                                                                    String reply = scanner.nextLine();
                                                                    if(reply.equals("OK")) {
                                                                        break;
                                                                    }
                                                                }
                                                                else {
                                                                    System.out.println("All available publishers");
                                                                    amazonBook.showPublishers();
                                                                    System.out.print("Choose publisher(enter number): ");

                                                                    try {
                                                                        int publisherStt = scanner.nextInt();

                                                                        String name  = amazonBook.findPublisher(publisherStt);

                                                                        if(name.isEmpty())  {
                                                                            System.out.println(Support.redColor("The field cannot be left blank"));
                                                                        }else {
                                                                            publisher = Support.capitalizeText(name);
                                                                            System.out.println(Support.greenColor("Added " +publisher + " as publisher for book success"));
                                                                            publisherRun = false;
                                                                            break;
                                                                        }
                                                                    }
                                                                    catch (InputMismatchException error) {
                                                                        logErrorSyntax();
                                                                        clearBufferMemory(scanner);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        else if (publisherOption == 2) {
                                                            boolean publisherOptionTwo = true;
                                                            while (publisherOptionTwo) {
                                                                System.out.println("Enter a new publisher(required): ");
                                                                String publisherName =  scanner.nextLine();


                                                                if(publisherName.isEmpty()) {
                                                                    System.out.println(Support.redColor("The field cannot be left blank"));
                                                                    break;
                                                                }
                                                                else {
                                                                    publisher = publisherName;
                                                                    System.out.println(Support.greenColor("Add " + publisher +  " for book success"));
                                                                    break;
                                                                }
                                                            }
                                                            break;

                                                        }
                                                        else {
                                                            logErrorSyntax();
                                                        }
                                                    }
                                                    catch (InputMismatchException error) {
                                                        logErrorSyntax();
                                                        clearBufferMemory(scanner);
                                                    }
                                                }

                                                // save info of author
                                                System.out.println(authorName + "\n" + authorEmail + "\n" + authorPhone + "\n" + authorNationality + "\n" + authorDescription + "\n" + title + "\n" +  genre + "\n" +  sellingPrice + "\n" +  pageNumber + "\n" +  isbnCode  + "\n" +  pubYear + "\n" + publisher + "\n" + bookQuantity);

                                                amazonBook.add(authorName,authorEmail, authorPhone, authorNationality, authorDescription, title, genre, sellingPrice, pageNumber, isbnCode, pubYear, publisher, bookQuantity);


                                            }
//                                            else if (option == 2) {
//                                                System.out.println("----------------------Update book--------------------");
//                                                System.out.println("Update the book's information, if you want to keep the previous data, leave it blank\n");
//
//                                                System.out.print("Enter book id is needed update:");
//                                                int bookId = scanner.nextInt();
//                                                scanner.nextLine();
//
//                                                System.out.print("Title:");
//                                                title =  scanner.nextLine();
//
//                                                System.out.print("Genre:");
//                                                genre =  scanner.nextLine();
//
//                                                System.out.print("Selling price:");
//                                                float price = scanner.nextFloat();
//                                                sellingPrice = formatPrice(price);
//                                                scanner.nextLine();
//
//                                                System.out.print("Author:");
//                                                authorName =  scanner.nextLine();
//
//                                                System.out.print("Page number:");
//                                                pageNumber =  scanner.nextInt();
//                                                scanner.nextLine();
//
//                                                System.out.print("ISBN code:");
//                                                isbnCode =  scanner.nextLine();
//
//                                                System.out.print("Publication year:");
//                                                pubYear =  scanner.nextLine();
//
//                                                System.out.print("Publisher:");
//                                                publisher =  scanner.nextLine();
//
//
////                                                amazonBook.update(bookId, title, genre,sellingPrice, pageNumber, isbnCode, pubYear, publisher , author);
//
//                                            }
                                            else if (option == 3) {
                                                System.out.println("---------------------Delete book----------------------");
                                                System.out.println("To delete a book enter the id");
                                                System.out.print("Enter id of the book:");

                                                try {
                                                    int bookId = scanner.nextInt();
                                                    scanner.nextLine();

                                                    amazonBook.delete(bookId);
                                                }
                                                catch (InputMismatchException error) {
                                                    logErrorSyntax();
                                                    clearBufferMemory(scanner);
                                                }

                                            }
                                            else if (option == 0) {
                                                break;
                                            }
                                            else {
                                                logErrorSyntax();
                                            }

                                            // clean

                                            title = "";
                                            genre ="" ;
                                            authorName=""  ;
                                            authorEmail="";
                                            authorNationality="";
                                            authorPhone="" ;
                                            authorDescription="";
                                            isbnCode ="" ;
                                            publisher ="" ;
                                            pubYear = "";
                                            pageNumber = 0;
                                            sellingPrice = 0;

                                            amazonBook.getBooks();
                                            amazonBook.getGenres();
                                            amazonBook.getAuhors();
                                            amazonBook.getNationalities();
                                            amazonBook.getPublishers();

                                        }
                                        catch (InputMismatchException err) {
                                            logErrorSyntax();
                                            clearBufferMemory(scanner);
                                        }
                                    }
                                }
                            else {
                                logErrorSyntax();
                                clearBufferMemory(scanner);
                            }
                        }
                        catch (InputMismatchException error) {
                            logErrorSyntax();
                            clearBufferMemory(scanner);
                        }

                    }

                }
    }
}
