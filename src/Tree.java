public class Tree
{
    private int nodeCount;
    private Node root;
    private boolean isRespectful;


    public Tree()
    {
    }

    public Tree(int nodeCount, Node root)
    {
        this.nodeCount = nodeCount;
        this.root = root;
    }

    public Tree(int nodeCount, Node root, boolean isRespectful)
    {
        this.nodeCount = nodeCount;
        this.root = root;
        this.isRespectful = isRespectful;
    }

    public Tree copy()
    {
        Tree tree = new Tree(nodeCount, root.copy());
        setParent(tree.root, tree.root.getSon());
        setParent(tree.root, tree.root.getDaughter());
        return tree;
    }

    private void setParent(Node parent, Node child)
    {
        if (child == null)
        {
            return;
        }

        if (!child.hasParent())
        {
            child.setParent(parent);
        }
        setParent(child, child.getSon());
        setParent(child, child.getDaughter());

    }

    //region getters and setters

    public int getNodeCount()
    {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount)
    {
        this.nodeCount = nodeCount;
    }

    public Node getRoot()
    {
        return root;
    }

    public void setRoot(Node root)
    {
        this.root = root;
    }

    public boolean isRespectful()
    {
        return isRespectful;
    }

    public void setRespectful(boolean respectful)
    {
        isRespectful = respectful;
    }

    //endregion

    public Node getEqualsNode(Node node, Node referenceNode)
    {
        if (!node.equals(referenceNode))
        {
            getEqualsNode(node.getSon(), referenceNode);
            getEqualsNode(node.getDaughter(), referenceNode);
        }
        return node;
    }

    public void incrementNodeCount()
    {
        nodeCount++;
    }

    public void incrementNodeCount(int count)
    {
        nodeCount += count;
    }

    public void setNamesForNodes()
    {
        Integer i = 0;
        Node son = root;
        while (son != null)
        {
            son.setName((i++).toString());
            son = son.getSon();
        }
        Node daughter = root.getDaughter();
        while (daughter != null)
        {
            daughter.setName((i++).toString());
            daughter = daughter.getDaughter();
        }
    }
}
