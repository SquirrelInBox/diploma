import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Map<Integer, List<Tree>> trees = generateTrees(4);
        System.out.println(trees);
        saveTrees(trees);
    }

    private static Map<Integer, List<Tree>> generateTrees(int listCount)
    {
        Map<Integer, List<Tree>> trees = new HashMap<>();

        Tree previousRoot = new Tree(1, new Node(null, null, null, 1), true);
        trees.put(1, Collections.singletonList(previousRoot));

        for (int i = 1; i < listCount; i++)
        {
            List<Tree> currentTrees = trees.get(i);
            List<Tree> resultTrees = new ArrayList<>();
            for (Tree tree : currentTrees)
            {
                Tree currentRoot = tree.copy();

                Node currentNode = currentRoot.getRoot();
                tryBuildRespectfulTree(currentRoot, currentNode, resultTrees);
            }
            trees.put(i + 1, resultTrees);
        }


        return trees;
    }

    private static void tryBuildRespectfulTree(Tree tree, Node currentNode, List<Tree> trees)
    {
        if (currentNode.isLeaf())
        {
            Tree leftTree = tree.copy();
            Node newNode = leftTree.getEqualsNode(leftTree.getRoot(), currentNode);
            newNode.setSon(new Node(newNode, null, null, 1));
            newNode.setDaughter(new Node(newNode, null, null, 1));
            newNode.getSon().setParent(newNode);
            newNode.getDaughter().setParent(newNode);
            newNode.incrementLeafCount(newNode);
            leftTree.incrementNodeCount(2);
            leftTree.setRespectful(isRespectfulTree(leftTree, newNode));
            trees.add(leftTree);

        }
        else
        {
            tryBuildRespectfulTree(tree, currentNode.getSon(), trees);
            tryBuildRespectfulTree(tree, currentNode.getDaughter(), trees);
        }

    }

    private static boolean isRespectfulTree(Tree tree, Node currentNode)
    {

        //todo finish it
        if (!currentNode.hasDaughter() && !currentNode.hasSon())
        {
            return false;
        }


        return false;
    }

    public static void saveTrees(Map<Integer, List<Tree>> trees) throws IOException
    {
        for (int i : trees.keySet())
        {
            for (Tree tree : trees.get(i))
            {
                OutputStream os = new FileOutputStream(String.format("in%s.txt", i));
                writeTitle(os);
                String treeLine = "\\" + Node.writeNode(tree.getRoot()) + ";";
                os.write(treeLine.getBytes(), 0, treeLine.length());
                writeFinish(os);
            }
        }
    }

    public static void writeTitle(OutputStream os) throws IOException
    {
        String title =
                "\\documentclass{article}\n" +
                "\\usepackage{tikz}\n" +
                "\\begin{document}\n" +
                "\\begin{tikzpicture}\n";
        os.write(title.getBytes(), 0, title.length());
    }

    public static void writeFinish(OutputStream os) throws IOException
    {
        String end =
                "\n\\end{tikzpicture}\n" +
                "\\end{document}\n";
        os.write(end.getBytes(), 0, end.length());
    }
}
