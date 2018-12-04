# GitGraph

**The Project GitGraphServer can be accessed at https://github.com/xuanyi531/GitGraphServer.**

Knowledge Graph for Commit History of a Git repository. 

The project is built with Maven. "Main" is the entry class.

*Step 1:* Open the java project ./GitGraph and then clone a Git project to analyse.

e.g. git clone https://github.com/rahulrj/YahooNewsOnboarding.git

*Step 2:* The command line parameters are -p, -f and -d.

**-p &lt;project_path&gt; -f [&lt;filtering_options&gt;] -d [&lt;database_output_path&gt;]**

p  - -filter pictures

a  - -filter audio

v  - -filter videos

c  - -customize 

<u>The above filtering content can be modified in \src\main\java\helper\TypeFilter.java.</u>

**e.g.  java -jar gitgraph.jar -p ./YahooNewsOnboarding -f pavc -d D:/neo4j/data/databases/graph.db** means that you want to filter pics, audio, videos and some customized filetypes.

*Step 3:* Download and install Neo4j https://neo4j.com/ . View the knowledge graph at http://localhost:7474

- Remember to change the default settings of Initial Node Display to show more Nodes.

- Use the Cypher statement "**MATCH p=()-->() RETURN p**" to show all the Nodes and Relationships.

- You can change the color of Nodes and Relationships as you like with Neo4j.

- If you wanna check a specific class like "MainActivity", use "**MATCH (b:Class) WHERE b.name="MainActivity" RETURN b**". You just need to double-click on the node to see other nodes and relationships associated with it.


*More Cypher Statements cases:*

- MATCH (c:Commit)-->(f:File)-->(z:Class)-->(m:Method) WHERE c.type = "fixbug" AND m.create_commit = c.name RETURN m.name, m.class, count(m) AS cnt ORDER BY cnt DESC LIMIT 10

- MATCH (c:Commit)-->(f:File) WHERE c.type = "fixbug" AND f.type = "java" AND f.create_commit = c.name RETURN f.name, c.developer

- MATCH(c:Commit) WHERE c.type="fixbug" RETURN count(*)

- Get more information at https://neo4j.com/developer/cypher-query-language/.




