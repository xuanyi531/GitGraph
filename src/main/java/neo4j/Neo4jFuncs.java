package neo4j; /**
 * Created by Carol on 2018/11/28.
 */

import helper.Configuration;
import helper.Utils;
import model.ApiObject;
import model.ClassObject;
import model.FileObject;
import model.MethodObject;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

public class Neo4jFuncs {

    public GraphDatabaseService db;


    public Neo4jFuncs() {
        GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
        File database;
        if (Configuration.database != null){
            database = new File(Configuration.database);
        }else{
            database = new File(Configuration.project + ".db");
        }
        if (database.exists())
            Utils.deleteDir(database);
        this.db = dbFactory.newEmbeddedDatabase(database);
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    public Node createBranchNode(String branch_name) {
        Node node;
        try (Transaction tx = db.beginTx()) {

            // 创建节点
            node = db.createNode(GitLables.Branch);
            node.setProperty("name", branch_name);

            tx.success();
        }
        return node;
    }

    public Node createCommitNode(String commit_name, String developer,
                                 int time, String commit_message) {
        Node node;
        try (Transaction tx = db.beginTx()) {

            // 创建节点
            node = db.createNode(GitLables.Commit);
            node.setProperty("name", commit_name);
            node.setProperty("developer", developer);
            node.setProperty("time", time);
            node.setProperty("message", commit_message);
            String[] blacklist = {"fix", "bug"};
            boolean isFix = false;
            for (String bad_word : blacklist){
                if (commit_message.toLowerCase().contains(bad_word)){
                    node.setProperty("type", "fixbug");
                    isFix = true;
                }
            }
            if (!isFix){
                node.setProperty("type", "normal");
            }
            tx.success();
        }
        return node;
    }

    public Node createFileNode(FileObject f, String commit_name) {
        Node node;
        try (Transaction tx = db.beginTx()) {

            // 创建节点
            node = db.createNode(GitLables.File);
            node.setProperty("name", f.getName());
            node.setProperty("path", f.getPath());
            node.setProperty("content", f.getFiledata());
            node.setProperty("content_md5", f.getMd5());
            node.setProperty("type", f.getType());
            node.setProperty("create_commit", commit_name);

            tx.success();
        }
        return node;
    }

    public Node createClassNode(ClassObject co, String commit_name) {
        Node node;
        try (Transaction tx = db.beginTx()) {

            // 创建节点
            node = db.createNode(GitLables.Class);
            node.setProperty("name", co.getName());
            node.setProperty("file", co.getFile());
            node.setProperty("content_md5", co.getBodyMd5());
            node.setProperty("path_signature", co.getSignMd5());
            node.setProperty("create_commit", commit_name);
            node.setProperty("package_name", co.getPackageName());

            tx.success();
        }
        return node;
    }

    public Node createMethodNode(MethodObject mo, String commit_name) {
        Node node;
        try (Transaction tx = db.beginTx()) {

            // 创建节点
            node = db.createNode(GitLables.Method);
            node.setProperty("name", mo.getName());
            node.setProperty("body", mo.getBody());
            node.setProperty("content_md5", mo.getBodyMd5());
            node.setProperty("class", mo.getClazz());
            node.setProperty("file", mo.getFile());
            node.setProperty("path_signature", mo.getSignMd5());
            node.setProperty("create_commit", commit_name);

            tx.success();
        }
        return node;
    }

    public Node createAPINode(ApiObject ao) {
        Node node;
        try (Transaction tx = db.beginTx()) {

            // 创建节点
            node = db.createNode(GitLables.API);
            node.setProperty("name", ao.getSign());

            tx.success();
        }
        return node;
    }

    public void createRelationship(Node node1, Node node2, GitRelationships rel) {
        try (Transaction tx = db.beginTx()) {
            if(rel == GitRelationships.MethodtoAPI){
                Iterable<Relationship> rels = node1.getRelationships();
                Boolean flag = true;
                for(Relationship relationship : rels){
                    if (relationship.getOtherNode(node1).equals(node2)) {
                        flag = false;
                        break;
                    }
                }
                if(flag)
                    node1.createRelationshipTo(node2, rel);
            }else{
                node1.createRelationshipTo(node2, rel);
            }

            tx.success();
        }
    }

    public Node matchFileMD5(FileObject fo){
        Node node;
        try (Transaction tx = db.beginTx()) {
            node = db.findNode(GitLables.File, "content_md5", fo.getMd5());
            tx.success();
        }
        return node;
    }

    public Node matchClassMD5(ClassObject co){
        Node node = null;
        try (Transaction tx = db.beginTx()) {
            ResourceIterator<Node> result = db.findNodes(GitLables.Class, "content_md5", co.getBodyMd5());
            while (result.hasNext()) {
                node = result.next();
                if (node.getProperty("path_signature").equals(co.getSignMd5())) {
                    break;
                }
            }
            tx.success();
        }
        return node;
    }

    public Node matchMethodMD5(MethodObject mo){
        Node node = null;
        try (Transaction tx = db.beginTx()) {
            ResourceIterator<Node> result = db.findNodes(GitLables.Method, "content_md5", mo.getBodyMd5());
            while (result.hasNext()) {
                node = result.next();
                if (node.getProperty("path_signature").equals(mo.getSignMd5())) {
                    break;
                }
            }
            tx.success();
        }
        return node;
    }

    public Node matchAPI(ApiObject ao){
        Node result;
        try (Transaction tx = db.beginTx()) {
            result = db.findNode(GitLables.API, "name", ao.getSign());
            tx.success();
        }
        return result;
    }

}



