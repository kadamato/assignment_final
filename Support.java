import java.util.ArrayList;

public  class Support {




    public static ArrayList<Book>  sortAuthor (ArrayList<Book>  books) {

        ArrayList<Book> listBook =  new ArrayList<>(books);
        int length = listBook.size();
        // sort a-z

        for (int i = 0; i < length - 1; i++) {
            int maxIndex = i;
            int charCodeOfI = listBook.get(i).author.codePointAt(0);

            for (int j = i + 1; j < length; j++) {
                int charCodeOfJ  = listBook.get(j).author.codePointAt(0);
                boolean charCodeSmallest =  charCodeOfJ > charCodeOfI;
                if(charCodeSmallest)
                    maxIndex = j;
            }

            Book book1 =  listBook.get(i);
            Book book2 =  listBook.get(maxIndex);

            listBook.set(maxIndex, book1);
            listBook.set(i, book2);




        }
        return listBook;
    }

    public static ArrayList<Book>  sortPrice (ArrayList<Book> books) {
        ArrayList<Book>  listBook = new ArrayList<>(books);
        int length = listBook.size();

        for (int i = 0; i < length - 1; i++) {
            int maxIndex = i;
            float priceOne = listBook.get(i).sellingPrice;

            for (int j = i + 1; j < length; j++) {
               float priceTwo = listBook.get(j).sellingPrice;
                boolean priceSmallest = priceTwo >=  priceOne;
                if(priceSmallest)
                    maxIndex = j;
            }

            Book bookCurrent =  listBook.get(i);
            Book bookMax =  listBook.get(maxIndex);

            listBook.set(maxIndex, bookCurrent);
            listBook.set(i, bookMax);

        }
        return listBook;
    }


    public static String italicText (String text) {

        String italic = "\033[3m";

        String reset = "\033[0m";

        return italic + text + reset;

    }

    public  static String  boldText (String text) {

        String bold = "\033[1m";

        String reset = "\033[0m";


        return bold + text + reset;
    }


    public  static String redColor (String text) {
        String reset = "\u001B[0m";
        String red = "\u001B[38;2;216;0;50m";
        return red + text + reset;
    }

    public static String capitalizeText (String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public static String greenColor (String text) {
        String reset = "\u001B[0m";
        String red = "\u001B[38;2;103;194;97m";
        return red + text + reset;
    }
     public  static  void main(String[] args) {










    }
}