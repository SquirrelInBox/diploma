import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree
{
    private int nodeCount;
    private Node root;
    private boolean isRespectful;
    private List<Node> leafs = new ArrayList<>();

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

    public static Tree createFrom(Tree tree, int leafIndex)
    {
        Tree currentTree = tree.copy();
        Node node = currentTree.getLeafs().get(leafIndex);
        currentTree.getLeafs().remove(node);

        node.setSon(new Node(node, null, null, 1));
        node.setDaughter(new Node(node, null, null, 1));
        node.getSon().setParent(node);
        node.getDaughter().setParent(node);
        node.incrementLeafCount(node);
        currentTree.incrementNodeCount(1);
        currentTree.getLeafs().addAll(
                Arrays.asList(node.getSon(), node.getDaughter()));
        setRespectfulInTree(currentTree);

        return currentTree;
    }

    private static void setRespectfulInTree(Tree tree)
    {
        tree.setRespectful(true);
        for (Node node : tree.leafs)
        {
            if (!isRespectfulInNode(node) || !isRespectfulInNode(node.getParent()))
            {
                tree.setRespectful(false);
                break;
            }
        }
    }

    private static boolean isRespectfulInNode(Node node)
    {
        if (node == null || node.getParent() == null)
        {
            return true;
        }

        Node nephew = node.isMan() ? node.getParent().getDaughter().getSon() : node.getParent().getSon().getDaughter();
        return contains(node,nephew);
    }

    public Tree copy()
    {
        Tree tree = new Tree();
        tree.setNodeCount(nodeCount);
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

    public List<Node> getLeafs()
    {
        return leafs;
    }

    public void setLeafs(List<Node> leafs)
    {
        this.leafs = leafs;
    }

    //endregion

    public Node getEqualsNode(Node node, Node referenceNode)
    {
        if (!node.equals(referenceNode))
        {
            if (node.getSon() != null)
                node = getEqualsNode(node.getSon(), referenceNode);
            if(node == null || node.getDaughter() != null)
                node = getEqualsNode(node.getDaughter(), referenceNode);

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
