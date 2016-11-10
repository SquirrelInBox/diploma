import java.io.IOException;

public class Node
{
    private String name;
    private Node parent;
    private Node son;
    private Node daughter;
    private int leafCount;

    public Node(Node parent, Node son, Node daughter, int listCount)
    {
        this.parent = parent;
        this.son = son;
        this.daughter = daughter;
        this.leafCount = listCount;
    }


    //region getters and setters

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public Node getDaughter()
    {
        return daughter;
    }

    public void setDaughter(Node daughter)
    {
        this.daughter = daughter;
    }

    public Node getSon()
    {
        return son;
    }

    public void setSon(Node son)
    {
        this.son = son;
    }

    public int getLeafCount()
    {
        return leafCount;
    }

    public void setLeafCount(int leafCount)
    {
        this.leafCount = leafCount;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    //endregion

    public boolean isLeaf()
    {
        return daughter == null && son == null;
    }

    public boolean isRoot()
    {
        return parent == null;
    }

    public boolean hasSon()
    {
        return son != null;
    }

    public boolean hasDaughter()
    {
        return daughter != null;
    }

    public Node copy(Tree tree)
    {
        Node daughter = null;
        Node son = null;
        if (this.son != null) {
            son = this.son.copy(tree);
        }

        if (this.daughter != null) {
            daughter = this.daughter.copy(tree);
        }
        Node node = new Node(null, son, daughter, leafCount);
        if (son == null && daughter == null)
        {
            tree.getLeafs().add(node);
        }
        return node;
    }

    public boolean hasBrother()
    {
        return parent != null && parent.hasSon();
    }

    private boolean hasSister()
    {
        return parent != null && parent.hasDaughter();
    }

    public boolean hasParent()
    {
        return parent != null;
    }

//    public boolean equals(Node node)
//    {
//
//        return this.leafCount == node.getLeafCount() &&
//                (!hasSon() && !node.hasSon() || this.son.equals(node.getSon())) &&
//                (!hasDaughter() && !node.hasDaughter() || this.daughter.equals(node.getDaughter()));
//    }
//
//    public int hashCode(Node node)
//    {
//        return node.getParent().hashCode() + node.getSon().hashCode() + node.getDaughter().hashCode();
//    }

    public void incrementLeafCount()
    {
        leafCount++;
    }

    public void incrementLeafCount(Node node)
    {
        while (node != null)
        {
            node.incrementLeafCount();
            node = node.getParent();
        }
    }

    public boolean isMan()
    {
        return parent.getSon() == this;
    }

    public static String writeNode(Node node) throws IOException
    {
        return String.format(
                "node[circle, draw]{%s}\n" +
                "child%s\n" +
                "child%s\n", node.getName(),
                node.getSon() == null ? "[missing]{}" : "{" + writeNode(node.getSon()) + "}",
                node.getDaughter() == null ? "[missing]{}" : "{" + writeNode(node.getDaughter()) + "}");

    }
}
