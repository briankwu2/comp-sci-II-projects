// netID: bkw180001
// name: Brian Wu

public class BSTree <G extends Comparable<G>> {

    // Member fields
    private Node<G> rootPtr;

    // Default Constructor
    public BSTree()
    {
        rootPtr = null;
    }

    // Overloaded Constructor
    public BSTree(Node<G> rootPtr)
    {
        this.rootPtr = rootPtr;
    }

    // Mutator
    public void setRootPtr(Node<G> rootPtr)
    {
        this.rootPtr = rootPtr;
    }

    // Accessor
    public Node<G> getRootPtr()
    {
        return rootPtr;
    }

    // Insert Function

    public void BSTInsert(Node<G> insertNode)
    {
        if (rootPtr == null)
        {
            rootPtr = insertNode; // If the BST is empty, then assign the root ptr with the node.
        }
        else
        {
            BSTInsertHelper(rootPtr, insertNode); // Otherwise call the helper function.

        }

    }

    public void BSTInsertHelper(Node<G> parentNode, Node<G> insertNode)
    {
        if (parentNode.getPayload().compareTo(insertNode.getPayload()) > 0) 
        {
            if (parentNode.getLeftPtr() == null)
            {
                parentNode.setLeftPtr(insertNode);
            }
            else
            {
                BSTInsertHelper(parentNode.getLeftPtr(), insertNode); // Recursively call this function with the left child.
            }
        }
        else // If the parentNode is greater than the insertNode
        {
            if (parentNode.getRightPtr() == null)
            {
                parentNode.setRightPtr(insertNode);
            }
            else
            {
                BSTInsertHelper(parentNode.getRightPtr(), insertNode); // Recursively call this function with the right child.
            }
        }
    }


    // Search function
    public Node<G> BSTSearch (G key)
    {
        return BSTSearchHelper(rootPtr, key); // Call the helper function
    }

    public Node<G> BSTSearchHelper (Node<G> parentNode, G key)
    {

        if (parentNode != null)
        {
            if (parentNode.getPayload().compareTo(key) == 0)
            {
                return parentNode;
            }
            else if (key.compareTo(parentNode.getPayload()) < 0)
            {
                return BSTSearchHelper(parentNode.getLeftPtr(), key);
            }
            else if (key.compareTo(parentNode.getPayload()) > 0)
            {
                return BSTSearchHelper(parentNode.getRightPtr(), key);
            }
        }

        return null; // If no node is ever found matching that key, then return null.

    }

    public Node<G> BSTGetParentNode(Node<G> childNode)
    {
        return BSTGetParentHelper(rootPtr, childNode);

    }

    // Find Parent node function for the delete function.
    public Node<G> BSTGetParentHelper(Node<G> parentNode, Node<G> childNode)
    {
        if (parentNode == null)
        {
            return null;
        }
        if((parentNode.getLeftPtr() == childNode) || (parentNode.getRightPtr() == childNode))
        {
            return parentNode;
        }
        if (childNode.getPayload().compareTo(parentNode.getPayload()) < 0)
        {
            return BSTGetParentHelper(parentNode.getLeftPtr(), childNode);
        }
        else
        {
            return BSTGetParentHelper(parentNode.getRightPtr(), childNode);
        }
    }


    // Delete function 

    public boolean BSTDelete (G deleteKey)
    {
        Node<G> deleteNode = BSTSearch(deleteKey);
        Node<G> parentNode = BSTGetParentNode(deleteNode);
        return BSTDeleteHelper(parentNode, deleteNode);
    }

    public boolean BSTDeleteHelper (Node<G> parentNode, Node<G> deleteNode)
    {
        if (deleteNode == null)
        {
            return false;
        }

        //Case 1: Delete node is internal with two child nodes.
        if ((deleteNode.getLeftPtr() != null) && (deleteNode.getRightPtr() != null))
        {
            Node<G> successorNode = deleteNode.getRightPtr();
            Node<G> successorParent = deleteNode;

            // Finds delete node's successor.
            while (successorNode.getLeftPtr() != null)
            {
                successorParent = successorNode;
                successorNode = successorNode.getLeftPtr();
            }

            deleteNode.setPayload(successorNode.getPayload()); // Replaces the delete node position with its successor node.
            BSTDeleteHelper(successorParent, successorNode); // Will delete the successor node now.
        }

        //Case 2: Root node with one or no children.
        else if (deleteNode == rootPtr)
        {
            if (deleteNode.getLeftPtr() != null)
            {
                rootPtr = deleteNode.getLeftPtr(); // Root node will be replaced with it's left child.
            }
            else
            {
                rootPtr = deleteNode.getRightPtr(); // Root node will be replaced with it's right child.
            }
        }

        //Case 3: Internal node with left child only
        else if (deleteNode.getLeftPtr() != null)
        {
            if (parentNode.getLeftPtr() == deleteNode)
            {
                parentNode.setLeftPtr(deleteNode.getLeftPtr()); // Assigns the parentNode's left child to be our deleteNode's left child.
            }
            else
            {
                parentNode.setRightPtr(deleteNode.getLeftPtr()); // Assigns the parentNode's right child to be our deleteNode's left child.
            }
        }

        //Case 4: Internal node with right child only or it is a leaf node (no child nodes)
        else
        {
            if (parentNode.getLeftPtr() == deleteNode)
            {
                parentNode.setLeftPtr(deleteNode.getRightPtr());
            }
            else
            {
                parentNode.setRightPtr(deleteNode.getRightPtr());
            }
        }

        return true; // Means that our node was successfully found and removed.
    }







    







    
}
