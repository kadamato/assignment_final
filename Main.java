import java.util.Arrays;
import java.util.Scanner;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.regex.*;


public class Main {


     public static float formatPrice (float price) {
         String formatPrice  =  String.format("%.2f", price);
         float handledPrice  =  Float.parseFloat(formatPrice);
         return handledPrice;

     }
    public static boolean formatIsbnCode (String isbnCode ) {
        String regex = "\\d{3}-\\d{10}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(isbnCode);

        if(matcher.matches()) {
            return true;
        }
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

    public static void main (String[] args)  {

                BookStore  amazonBook =  new BookStore("Amazon book", "2023-11-02");

                if(!BookStore.connectDB()) {
                    System.out.println("connect failed");
                }
                else {


                    Scanner scanner = new Scanner(System.in);
                    System.out.println("---------Book Store---------");
                    System.out.print("Are you user?[Y/N]");
                    String isWho = scanner.nextLine();

                    if(isWho.equals("y") || isWho.equals("Y")) {

                        System.out.println("Welcome to book store ");

                        System.out.println("1.Join store");

                        // command prefix
                        boolean start = true;
                        while(start) {
                            System.out.print(">");
                            int optionOne =  scanner.nextInt();
                            scanner.nextLine();

                            if(optionOne ==  1) {
                                // show all book
                                amazonBook.getAll();

                                System.out.println(amazonBook);

                                boolean runCommand = true;
                                while(runCommand) {
                                    System.out.print(">");
                                    String command = scanner.nextLine().trim();
                                    String[] parseCommand = command.split(" ");

                                    String keyword = parseCommand[0];
                                    String content = "";

                                    // validate  command
                                    for (int i = 0 ; i < parseCommand.length ; i++ ) {
                                        if(i == 0) {
                                            keyword =  parseCommand[0];
                                        }
                                        else if(parseCommand[i].isEmpty()) {
                                            continue;
                                        }
                                        else if (content.isEmpty()) {
                                            content += parseCommand[i];
                                        }
                                        else {
                                            content += " " +  parseCommand[i];
                                        }
                                    }

                                    // syntax of commands
                                    if(keyword.startsWith("findAuthor")) {
                                        BookStore.filterAuthorName(content);
                                    }
                                    else if (keyword.startsWith("findBook")) {
                                        BookStore.findBook(content);
                                    }
                                    else if(keyword.startsWith("findGenre")) {
                                        BookStore.filterFollowGenre(content);
                                    }
                                    else if(keyword.startsWith("sortAuthor")) {
                                        BookStore.sortAuthor(content);
                                    }
                                    else if (keyword.startsWith("sortPrice")) {
                                        BookStore.sortPrice(content);
                                    }
                                    else if (keyword.startsWith("help")) {
                                        System.out.println("COMMAND");
                                        System.out.println("\t findAuthor, findBook, findGenre, sortAuthor, sortPrice");

                                        System.out.println("USAGE");
                                        System.out.println("\t findAuthor [bookAuthor] :find book follow name of the author\n" );
                                        System.out.println("\t findBook [bookName] :find book follow name of the book\n");
                                        System.out.println("\t findGenre [genre] :find book follow genre of the book\n");
                                        System.out.println("\t sortAuthor [a-z||z-a] :sort name of the author follow order from a-z(low-high) or z-a(high-low)\n");
                                        System.out.println("\t sortPrice [a-z||z-a] :sort price of the book follow order from a-z(low-high) or z-a(high-low)\n");
                                    }
                                    else {
                                        throw  new Error ("command is not valid");
                                    }
                                }

                            }
                            else {
                                System.out.println("command wrong , try again");
                            }
                        }
                    }

                    // menu for admin
                    else if (isWho.equals("N") || isWho.equals("n")){
                        boolean run = true;

                        while (run) {
                            System.out.println("\n");
                            System.out.println("Welcome back , this is admin mode \uD83C\uDF83");
                            System.out.println("1.add book \uD83D\uDCDA");
                            System.out.println("2.update book \uD83D\uDCD5");
                            System.out.println("3.delete book \uD83D\uDDD1\uFE0F");
                            System.out.println("0.return");
                            System.out.println("00.exit");
                            System.out.print(">");

                            int option = scanner.nextInt();
                            scanner.nextLine();

                            String title, genre, author, isbnCode, pubYear, publisher;
                            int pageNumber;
                            float sellingPrice;

                            if(option == 1) {
                                System.out.println("@Add book");

                                System.out.print("Title:");
                                title = scanner.nextLine();

                                System.out.print("Genre:");
                                genre = scanner.nextLine();

                                System.out.print("Selling price:");
                                float price =  scanner.nextFloat();
                                sellingPrice = formatPrice(price);
                                scanner.nextLine();

                                System.out.print("Author:");
                                author =  scanner.nextLine();

                                try {
                                    System.out.print("Page number(number):");
                                    pageNumber = scanner.nextInt();
                                    scanner.nextLine();
                                }
                                catch (InputMismatchException e) {
                                    System.out.println("Page number only can be number");
                                    break;
                                }


                                System.out.print("ISBN code:");
                                isbnCode =  scanner.nextLine();

                                boolean isFormat = Main.formatIsbnCode(isbnCode);
                                if(!isFormat) {
                                    System.out.println("ISBN code only can contain 14 digits follow pattern : ***-**********");
                                    break;
                                };


                                System.out.print("Publication year(yyyy-mm-dd):");
                                pubYear =  scanner.nextLine();

                                if(!formatPublicationYear(pubYear)) {
                                    System.out.println("Publication year must follow pattern yyyy-mm-dd");
                                    break;
                                }

                                System.out.print("Publisher:");
                                publisher =  scanner.nextLine();

                                // save info of author

                                amazonBook.add(author, title, genre, sellingPrice, pageNumber, isbnCode, pubYear, publisher);
                            }
                            else if (option == 2) {
                                System.out.println("@Update book");


                                System.out.print("Enter book id is needed update:");
                                int bookId = scanner.nextInt();
                                scanner.nextLine();


                                System.out.println("*Note:Enter info book into field , otherwise leave it blank");

                                System.out.print("Title:");
                                title =  scanner.nextLine();

                                System.out.print("Genre:");
                                genre =  scanner.nextLine();

                                System.out.print("Selling price:");
                                float price = scanner.nextFloat();
                                sellingPrice = formatPrice(price);
                                scanner.nextLine();

                                System.out.print("Author:");
                                author =  scanner.nextLine();

                                System.out.print("Page number:");
                                pageNumber =  scanner.nextInt();
                                scanner.nextLine();

                                System.out.print("ISBN code:");
                                isbnCode =  scanner.nextLine();

                                System.out.print("Publication year:");
                                pubYear =  scanner.nextLine();

                                System.out.print("Publisher:");
                                publisher =  scanner.nextLine();


                                amazonBook.update(bookId, title, genre,sellingPrice, pageNumber, isbnCode, pubYear, publisher , author);

                            }
                            else if (option == 3) {
                                System.out.println("@Delete book");

                                System.out.print("Enter id of the book:");
                                int bookId = scanner.nextInt();
                                scanner.nextLine();


                                amazonBook.delete(bookId);
                            }
                            else if (option == 0) {
                                break;
                            }
                            else if(option == 00) {
                                run = false;
                            }
                        }
                    }
                }
    }
}
