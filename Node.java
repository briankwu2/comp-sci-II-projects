// netID: bkw180001
// name: Brian Wu

public class Node <G extends Comparable<G>> {

    // Member Fields
    private Node<G> leftPtr;
    private Node<G> rightPtr;
    private G payload;

    // Methods

    // Constructor
    public Node ()
    {
        leftPtr = null;
        rightPtr = null;
        payload = null;
    }
    
    // Overloaded constructor
    public Node (G payload)
    {
        this.payload = payload;
        leftPtr = null;
        rightPtr = null;
    }

    // Mutators
    public void setLeftPtr (Node<G> leftPtr)
    {
        this.leftPtr = leftPtr;
    }

    public void setRightPtr(Node<G> rightPtr)
    {
        this.rightPtr = rightPtr;
    }

    public void setPayload(G payload)
    {
        this.payload = payload;
    }

    // Accessors
    public Node<G> getLeftPtr ()
    {
        return leftPtr;

    }

    public Node<G> getRightPtr()
    {
        return rightPtr;
    }

    public G getPayload()
    {
        return payload;
    }

}
