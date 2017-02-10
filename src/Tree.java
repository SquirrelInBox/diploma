public class Tree
{
    private Node root;
    private boolean isRespectful;

    public Tree()
    {
    }

    public Tree(Node root)
    {
        this.root = root;
    }

    public Tree(Node root, boolean isRespectful)
    {
        this.root = root;
        this.isRespectful = isRespectful;
    }

    public static void setRespectfulInTree(Tree tree)
    {
        Node son = tree.getRoot().getSon();
        Node daughter = tree.getRoot().getDaughter();
        boolean uncleIsRespectful = false;
        if (daughter != null)
        {
            uncleIsRespectful = contains(son, daughter.getSon());
        }
        boolean auntIsRespectful = false;

        if (son != null)
        {
            auntIsRespectful = contains(daughter, son.getDaughter());
        }

        tree.setRespectful(auntIsRespectful && uncleIsRespectful);
    }

    public Tree copy()
    {
        Tree tree = new Tree();
        tree.setRoot(root.copy(tree));
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

    public boolean equivalentTrees(Tree tree)
    {
        return tree != null && equivalentNode(root, tree.getRoot());
    }

    private boolean equivalentNode(Node first, Node second)
    {
        if (first == null && second == null)
        {
            return true;
        }
        return first !=null && second != null &&
                equivalentNode(first.getSon(), second.getSon()) &&
                equivalentNode(first.getDaughter(), second.getDaughter());
    }

    private static boolean contains(Node uncle, Node nephew)
    {
        return nephew == null ||
                uncle != null &&
                        contains(uncle.getSon(), nephew.getSon()) &&
                        contains(uncle.getDaughter(), nephew.getDaughter());
    }

}