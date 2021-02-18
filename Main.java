// netID: bkw180001
// name: Brian Wu

import java.io.*;
import java.util.Scanner;


public class Main
{
    public static void main (String [] args) throws IOException
    {
        boolean valid = false;


        Scanner scan = new Scanner(System.in);

        String invFileName, tranLogFileName;
        BSTree<DVD> invTree = null;

        while (!valid)
        {
            try
            {
                System.out.print("Enter the inventory file name: ");
                invFileName = scan.next();
                invTree = createBSTree(invFileName); // Creates the BSTree
                valid = true;

            }
            catch (Exception e)
            {
                valid = false;
                System.out.println("Either file could not be opened, or file not found. Please try again.");
            }
        }
       
        valid = false;
        while (!valid)
        {
            try
            {
                System.out.print("Enter the transaction log file name: ");
                tranLogFileName = scan.next();
                processTransactions(invTree, tranLogFileName); // Applies the transactions to the three.
                valid = true;
            }
            catch (Exception e)
            {
                valid = false;
                System.out.println("Either file could not be opened, or file not found. Please try again.");
            }

        }
        
        BSTPrintInorder(invTree.getRootPtr()); // Prints out the tree.
        scan.close();
    }


    public static BSTree<DVD> createBSTree(String invFileName) throws FileNotFoundException
    {
        Scanner inputScan = new Scanner(new File(invFileName));
        BSTree<DVD> invTree = null;
        int index = 0;

        while(inputScan.hasNext())
        {
            String processString = inputScan.nextLine();
            String[] DVDString = processString.split(new String(",")); // Parses the string at ","
            DVDString[0] = DVDString[0].substring(1, DVDString[0].length() - 1); // Gets rid of the " " at the end of the Title string.
            String titleString = DVDString[0];
            int numAvailable = Integer.parseInt(DVDString[1]);
            int numRented = Integer.parseInt(DVDString[2]);

            DVD tempDVD = new DVD(titleString, numAvailable, numRented);
            Node<DVD> DVDNode = new Node<DVD>(tempDVD);

            if (index == 0)
            {
                invTree = new BSTree<DVD>(DVDNode); // Sets the rootnode.
                index++;
                continue;
            }

            invTree.BSTInsert(DVDNode); // Inserts the node within the tree.
            index++;
        }

        return invTree;


    }

    public static void insertTitle(BSTree<DVD> invTree, String title, int numTitles)
    {
        DVD newDVD = new DVD(title, numTitles, 0);
        Node<DVD> newNode = new Node<DVD>(newDVD);
        invTree.BSTInsert(newNode);
    }

    public static void insertCopies(BSTree<DVD> invTree, String DVDTitle, int numTitles)
    {
        DVD tempDVD = new DVD(DVDTitle, 0,0); // Makes a dummy DVD to find the node that contains that title.

        Node<DVD> currNode = invTree.BSTSearch(tempDVD);
        int currAvailable = currNode.getPayload().getNumAvailable();
        currNode.getPayload().setNumAvailable(currAvailable + numTitles);

    }

    public static boolean removeCopies(BSTree<DVD> invTree, String titleName, int numToRemove) 
    {
        DVD searchDVD = new DVD(titleName, 0, 0); // Dummy DVD object to search for the correct node.
        Node<DVD> removeNode = invTree.BSTSearch(searchDVD);

        if (removeNode == null) // If it isn't found
        {
            return false;
        }

        int currAvailable = removeNode.getPayload().getNumAvailable();
        int currRented = removeNode.getPayload().getNumRented();

        if ((currAvailable - numToRemove <= 0) && currRented == 0)
        {
            invTree.BSTDelete(searchDVD);
        }
        else
        {
            removeNode.getPayload().setNumAvailable(currAvailable - numToRemove);
        }

        return true;
    }



    public static boolean rentDVD(BSTree<DVD> invTree, String DVDTitle)
    {
        DVD tempDVD = new DVD(DVDTitle, 0,0); // Makes a dummy DVD to find the node that contains that title.
        Node<DVD> currNode = invTree.BSTSearch(tempDVD);
        if (currNode == null)
        {
            return false;
        }

        currNode.getPayload().rentDVD();
        return true;
    }

    public static boolean returnDVD(BSTree<DVD> invTree, String DVDTitle)
    {
        DVD tempDVD = new DVD(DVDTitle, 0,0); // Makes a dummy DVD to find the node that contains that title.
        Node<DVD> currNode = invTree.BSTSearch(tempDVD);

        if (currNode == null)
        {
            return false;
        }

        currNode.getPayload().returnDVD();
        return true;
    }


    public static boolean validateTransaction (String process, String processOperation)
    {
        int lastIndex = -1;
        if (process.compareTo("rent") == 0 || process.compareTo("return") == 0)
        {
            // Determines if the rest of the string starts with "
            if (processOperation.charAt(0) != '"')
            {
                return false;
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
                return false;
            }
            // Checks if there is anything after the string.
            if (lastIndex + 1 != processOperation.length())
            {
                return false;
            }

            return true;

        }
        else if (process.compareTo("add") == 0 || process.compareTo("remove") == 0)
        {
            lastIndex = -1;

            // Determines if the rest of the string starts with "
            if (processOperation.charAt(0) != '"')
            {
                return false;
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
                return false;
            }
            // Checks for comma placement.
            if ((processOperation.length() <= lastIndex + 1) || processOperation.charAt(lastIndex + 1) != ',')
            {
                return false;
            }

            int numIndex = lastIndex + 2;

            if (processOperation.length() != numIndex + 1)
            {
                return false;
            }
            if (!Character.isDigit(processOperation.charAt(numIndex)))
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }

    }
    public static void processTransactions(BSTree<DVD> invTree, String inputFile) throws Exception
    {
        Scanner inputScan = new Scanner(new File(inputFile));
        PrintWriter toErrorLog = new PrintWriter(new File("error.log"));

        while (inputScan.hasNext())
        {
            try
            {
                String process = inputScan.next();
                String processOperation;
                String errorString;

                if (process.compareTo("add") == 0)
                {
                    processOperation = inputScan.nextLine();
                    errorString = process + processOperation;
                    processOperation = processOperation.strip(); // Gets rid of excess whitespace.
                    boolean valid = validateTransaction(process, processOperation); // Checks if the string is valid, and throws exception if not.
                    if (!valid)
                    {
                        throw new Exception(errorString);
                    }
        
                    String [] stringArray = processOperation.split(",");
                    
                    String tempTitle = stringArray[0].substring(1, stringArray[0].length() - 1); // Strips " " off the string.
                    int numTitles = Integer.parseInt(stringArray[1]);

                    DVD dummyDVD = new DVD(tempTitle, 0, 0);

                    if (invTree.BSTSearch(dummyDVD) == null)
                    {
                        //If the title does not yet exist, then add it to the BSTree.
                        insertTitle(invTree, tempTitle, numTitles); // Insert the new DVD into the tree.
                    }
                    else if (invTree.BSTSearch(dummyDVD) != null)
                    {
                        // Otherwise insert copies into existing title.
                        insertCopies(invTree, tempTitle, numTitles);
                    }

                }
                else if (process.compareTo("remove") == 0)
                {
                    processOperation = inputScan.nextLine();
                    errorString = process + processOperation; // In case there is an error, save the line.
                    processOperation = processOperation.strip(); // Gets rid of excess whitespace.
                    boolean valid = validateTransaction(process, processOperation);
                    if(!valid)
                    {
                        throw new Exception(errorString);
                    }
                    
                    String [] stringArray = processOperation.split(",");
                    
                    String tempTitle = stringArray[0].substring(1, stringArray[0].length() - 1); // Strips " " off the string.
                    int numTitles = Integer.parseInt(stringArray[1]);

                    boolean found = removeCopies(invTree, tempTitle, numTitles);

                    if (!found)
                    {
                        throw new Exception(errorString);
                    }

                }
                else if (process.compareTo("rent") == 0)
                {
                    // Format: rent "<title>"

                    processOperation = inputScan.nextLine();
                    errorString = process + processOperation;
                    processOperation = processOperation.strip(); // Gets rid of excess whitespace.
                    boolean valid = validateTransaction(process, processOperation);
                    if (!valid)
                    {
                        throw new Exception(errorString);
                    }

                    String tempTitle = processOperation.substring(1, processOperation.length() - 1); // Strips " " off the string.
                    boolean found = rentDVD(invTree, tempTitle);

                    if(!found)
                    {
                        throw new Exception(errorString);
                    }

                }
                else if (process.compareTo("return") == 0)
                {
                    processOperation = inputScan.nextLine();
                    errorString = process + processOperation;
                    processOperation = processOperation.strip(); // Gets rid of excess whitespace.
                    boolean valid = validateTransaction(process, processOperation);

                    if (!valid)
                    {
                        throw new Exception(errorString);
                    }

                    String tempTitle = processOperation.substring(1, processOperation.length() - 1); // Strips " " off the string.
                    boolean found = returnDVD(invTree, tempTitle);

                    if(!found)
                    {
                        throw new Exception(errorString);
                    }

                }

                else
                {
                    processOperation = "";
                    if (inputScan.hasNext())
                    {
                        processOperation = inputScan.nextLine();
                    }
                    errorString = process + processOperation;
                    throw new Exception(errorString);
                }

            }
            catch (Exception e)
            {
                toErrorLog.println(e.getMessage());
            }
           

        }

        toErrorLog.close();

    }
    
    public static void printReport(Node<DVD> node)
    {
        System.out.printf("%-30s\t%d\t%d", node.getPayload().getDVDTitle(), node.getPayload().getNumAvailable(), node.getPayload().getNumRented());
        System.out.println();


    }

    public static boolean BSTPrintInorder(Node<DVD> node)
    {
        if (node == null)
        {
            return true;
        }

        BSTPrintInorder(node.getLeftPtr());
        printReport(node);
        BSTPrintInorder(node.getRightPtr());
        return true;

    }

}