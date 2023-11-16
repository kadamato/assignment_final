import java.lang.reflect.Array;
import java.util.ArrayList;

public  class Sort {




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

    public  static  void main(String[] args) {










    }
}