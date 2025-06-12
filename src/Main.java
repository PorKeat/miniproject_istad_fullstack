import java.util.Scanner;

public class Main {
<<<<<<< HEAD

=======
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){

        String pName;
        Integer pQty;
        Double pPrice = 1.0,payment;

        System.out.print("""
                1. Input product name
                2. Display all products in card
                3. Search product in card
                4. Edit product's quantity
                5. Cancel product
                6. Back
                """);
        System.out.print("choose option: ");
        int op = new Scanner(System.in).nextInt();
        switch(op){
            case 1->{
                System.out.print("Input product name: ");
                pName = scanner.nextLine();
                System.out.print("Input product qty: ");
                pQty = scanner.nextInt();
                payment = pQty*pPrice;
             }
            }
        }
    }
>>>>>>> origin/taiyi
}
