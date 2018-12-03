package neo4j;

import org.neo4j.graphdb.Label;

public enum GitLables implements Label {
    Branch, Commit, File, Class, Method, API
}
