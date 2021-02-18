// netID: bkw180001
// name: Brian Wu

public class DVD implements Comparable<DVD> {
    
    // Member fields
    private String DVDTitle;
    private int numAvailable;
    private int numRented;

    // Overloaded Constructor
    public DVD (String DVDTitle, int numAvailable, int numRented)
    {
        this.DVDTitle = DVDTitle;
        this.numAvailable = numAvailable;
        this.numRented = numRented;
    }

    // Methods

    // Mutators
    public void setDVDTitle(String DVDTitle)
    {
        this.DVDTitle = DVDTitle;
    }

    public void setNumAvailable(int numAvailable)
    {
        this.numAvailable = numAvailable;
    }

    public void setNumRented(int numRented)
    {
        this.numRented = numRented;
    }

    // Accessors

    public String getDVDTitle ()
    {
        return DVDTitle;
    }


    public int getNumAvailable()
    {
        return numAvailable;
    }

    public int getNumRented()
    {
        return numRented;
    }

   // Overridden compareTo function.
    @Override
    public int compareTo(DVD o)
    {
        if (o instanceof DVD)
        {
            return DVDTitle.compareTo(o.DVDTitle);
        }
        return 0;
    }

    //Other methods
    public void rentDVD()
    {
        this.numAvailable--;
        this.numRented++;
    }

    public void returnDVD()
    {
        this.numAvailable++;
        this.numRented--;
    }

}
