
import java.util.Scanner;

public class Driver {
    
    public static void main(String[] args) {


        Scanner inputScan = new Scanner(System.in);

       

        while (true)
        {
            try 
            {
                System.out.print("Enter validation string: ");
        
                String process = inputScan.next();
                String processOperation = inputScan.nextLine();
                processOperation = processOperation.strip(); // Gets rid of excess whitespace.

                boolean valid = validateTransaction(process, processOperation);
                if (valid)
                {
                    System.out.println("Valid String");
                }
            }
            catch(Exception e)
            {
                System.out.println("Invalid String");
            }

        }


       


    }
    
    public static boolean validateTransaction (String process, String processOperation) throws Exception
    {
        if (process.compareTo("rent") == 0 || process.compareTo("return") == 0)
        {
            int lastIndex = -1;

            // Determines if the rest of the string starts with "
            if (processOperation.charAt(0) != '"')
            {
                throw new Exception();
            }

            // Checks the index of the last "
            for (int i = 1; i < processOperation.length(); i++)
            {
                if (processOperation.charAt(i) == '"')
                {
                    lastIndex = i;
                    break;
                }

            }

            // Checks if the ending of the " was found.
            if (lastIndex == -1)
            {
                throw new Exception();
            }
            // Checks if there is anything after the string.
            if (lastIndex + 1 != processOperation.length())
            {
                throw new Exception();
            }

            return true;

        }
        else if (process.compareTo("add") == 0 || process.compareTo("remove") == 0)
        {
            int lastIndex = -1;

            // Determines if the rest of the string starts with "
            if (processOperation.charAt(0) != '"')
            {
                throw new Exception();
            }

            // Checks the index of the last "
            for (int i = 1; i < processOperation.length(); i++)
            {
                if (processOperation.charAt(i) == '"')
                {
                    lastIndex = i;
                    break;
                }

            }

            // Checks if the ending of the " was found.
            if (lastIndex == -1)
            {
                throw new Exception();
            }
            // Checks for comma placement.
            if (processOperation.charAt(lastIndex + 1) != ',')
            {
                throw new Exception();
            }

            int numIndex = lastIndex + 2;

            if (processOperation.length() != numIndex + 1)
            {
                throw new Exception();
            }
            if (!Character.isDigit(processOperation.charAt(numIndex)))
            {
                throw new Exception();
            }

            return true;
        }
        else
        {
            throw new Exception();
        }

    }
}
