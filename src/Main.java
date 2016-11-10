import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Map<Integer, List<Tree>> trees = generateTrees(10);
        trees.keySet().forEach(i ->System.out.println(String.format("%s: %s", i, trees.get(i).size())));
        System.out.println(trees);
        saveTrees(trees);
    }

    private static Map<Integer, List<Tree>> generateTrees(int listCount)
    {
        Map<Integer, List<Tree>> trees = new HashMap<>();

        Tree previousRoot = new Tree(1, new Node(null, null, null, 1), true);
        previousRoot.getLeafs().add(previousRoot.getRoot());
        trees.put(1, Collections.singletonList(previousRoot));

        for (int i = 1; i < listCount; i++)
        {
            List<Tree> currentTrees = trees.get(i);
            List<Tree> resultTrees = new ArrayList<>();
            for (Tree tree : currentTrees)
            {
                List<Node> leafs = tree.getLeafs();
                for (int k = 0; k < leafs.size(); k++)
                {
                    Tree currentTree = Tree.createFrom(tree, k);

                    if (!currentTree.isRespectful() || isDouble(currentTree, resultTrees))
                    {
                        continue;
                    }
                    resultTrees.add(currentTree);
                }
            }
            trees.put(i + 1, resultTrees);
        }


        return trees;
    }

    private static boolean isDouble(Tree tree, List<Tree> trees)
    {
        for (Tree tempTree : trees)
        {
            if (tempTree.equivalentTrees(tree))
            {
                return true;
            }
        }

        return false;
    }

    public static void saveTrees(Map<Integer, List<Tree>> trees) throws IOException
    {
        for (int i : trees.keySet())
        {
            int j = 0;
            for (Tree tree : trees.get(i))
            {
                OutputStream os = new FileOutputStream(String.format("in%s%s.txt", i, j++));
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
