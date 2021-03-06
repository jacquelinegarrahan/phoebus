// Generated by delombok at Fri Apr 05 13:27:37 CEST 2019
/** 
 * Copyright (C) 2018 European Spallation Source ERIC.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.phoebus.applications.saveandrestore.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

/**
 * Superclass representing a node in a tree structure maintained by the save-and-restore service.
 * @author georgweiss
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node implements Comparable<Node> {
	@ApiModelProperty(required = false, value = "Database id of the node, defined by the server", allowEmptyValue = true)
	private int id;
	@ApiModelProperty(required = false, value = "Unique id of this node, defined in this class as a UUID. Client may override the default value through the setter. ", allowEmptyValue = true)
	private final String uniqueId;
	@ApiModelProperty(required = true, value = "Name of the folder or configuration")
	private String name;
	@ApiModelProperty(required = false, value = "Creation date, set by the server", allowEmptyValue = true)
	private Date created;
	@ApiModelProperty(required = false, value = "Last modified date, set by the server", allowEmptyValue = true)
	private Date lastModified;
	@ApiModelProperty(required = false, value = "Should be defined by the subclass.")
	private NodeType nodeType;
	@ApiModelProperty(required = true, value = "User name creating or modifying a node")
	private String userName;
	@ApiModelProperty(required = false, value = "Map of key/value pairs to be uses as collection of properties")
	private Map<String, String> properties;
	/**
	 * Do not change!!!
	 */
	public static final int ROOT_NODE_ID = 0;

	public static NodeBuilder configBuilder() {
		NodeBuilder nodeBuilder = new NodeBuilder();
		nodeBuilder.nodeType(NodeType.CONFIGURATION);
		return nodeBuilder;
	}

	public static NodeBuilder snapshotBuilder() {
		NodeBuilder nodeBuilder = new NodeBuilder();
		nodeBuilder.nodeType(NodeType.SNAPSHOT);
		return nodeBuilder;
	}

	public void putProperty(String key, String value) {
		if (properties == null) {
			properties = new HashMap<>();
		}
		properties.put(key, value);
	}

	public void removeProperty(String key) {
		if (properties != null) {
			properties.remove(key);
		}
	}

	public String getProperty(String key) {
		if (properties == null) {
			return null;
		}
		return properties.get(key);
	}

	@Override
	public boolean equals(Object other) {
		return nodeType.equals(((Node) other).getNodeType()) && uniqueId.equals(((Node) other).getUniqueId());
	}

	@Override
	public int hashCode() {
		return uniqueId.hashCode();
	}

	/**
	 * Implements strategy where folders are sorted before configurations (save sets), and
	 * equal node types are sorted alphabetically.
	 * @param other The tree item to compare to
	 * @return -1 if this item is a folder and the other item is a save set,
	 * 1 if vice versa, and result of name comparison if node types are equal.
	 */
	@Override
	public int compareTo(Node other) {
		if (nodeType.equals(NodeType.FOLDER) && other.getNodeType().equals(NodeType.CONFIGURATION)) {
			return -1;
		} else if (getNodeType().equals(NodeType.CONFIGURATION) && other.getNodeType().equals(NodeType.FOLDER)) {
			return 1;
		} else {
			return getName().compareTo(other.getName());
		}
	}

	@java.lang.SuppressWarnings("all")
	private static String $default$uniqueId() {
		return UUID.randomUUID().toString();
	}

	@java.lang.SuppressWarnings("all")
	private static NodeType $default$nodeType() {
		return NodeType.FOLDER;
	}


	@java.lang.SuppressWarnings("all")
	public static class NodeBuilder {
		@java.lang.SuppressWarnings("all")
		private int id;
		@java.lang.SuppressWarnings("all")
		private boolean uniqueId$set;
		@java.lang.SuppressWarnings("all")
		private String uniqueId;
		@java.lang.SuppressWarnings("all")
		private String name;
		@java.lang.SuppressWarnings("all")
		private Date created;
		@java.lang.SuppressWarnings("all")
		private Date lastModified;
		@java.lang.SuppressWarnings("all")
		private boolean nodeType$set;
		@java.lang.SuppressWarnings("all")
		private NodeType nodeType;
		@java.lang.SuppressWarnings("all")
		private String userName;
		@java.lang.SuppressWarnings("all")
		private Map<String, String> properties;

		@java.lang.SuppressWarnings("all")
		NodeBuilder() {
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder id(final int id) {
			this.id = id;
			return this;
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder uniqueId(final String uniqueId) {
			this.uniqueId = uniqueId;
			uniqueId$set = true;
			return this;
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder name(final String name) {
			this.name = name;
			return this;
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder created(final Date created) {
			this.created = created;
			return this;
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder lastModified(final Date lastModified) {
			this.lastModified = lastModified;
			return this;
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder nodeType(final NodeType nodeType) {
			this.nodeType = nodeType;
			nodeType$set = true;
			return this;
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder userName(final String userName) {
			this.userName = userName;
			return this;
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder properties(final Map<String, String> properties) {
			this.properties = properties;
			return this;
		}

		@java.lang.SuppressWarnings("all")
		public Node build() {
			String uniqueId = this.uniqueId;
			if (!uniqueId$set) uniqueId = Node.$default$uniqueId();
			NodeType nodeType = this.nodeType;
			if (!nodeType$set) nodeType = Node.$default$nodeType();
			return new Node(id, uniqueId, name, created, lastModified, nodeType, userName, properties);
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		public java.lang.String toString() {
			return "Node.NodeBuilder(id=" + this.id + ", uniqueId=" + this.uniqueId + ", name=" + this.name + ", created=" + this.created + ", lastModified=" + this.lastModified + ", nodeType=" + this.nodeType + ", userName=" + this.userName + ", properties=" + this.properties + ")";
		}
	}

	@java.lang.SuppressWarnings("all")
	public static NodeBuilder builder() {
		return new NodeBuilder();
	}


	@java.lang.SuppressWarnings("all")
	public static class NodeBuilderBuilder {
		@java.lang.SuppressWarnings("all")
		NodeBuilderBuilder() {
		}

		@java.lang.SuppressWarnings("all")
		public NodeBuilder build() {
			return Node.configBuilder();
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		public java.lang.String toString() {
			return "Node.NodeBuilderBuilder()";
		}
	}

	@java.lang.SuppressWarnings("all")
	public int getId() {
		return this.id;
	}

	@java.lang.SuppressWarnings("all")
	public String getUniqueId() {
		return this.uniqueId;
	}

	@java.lang.SuppressWarnings("all")
	public String getName() {
		return this.name;
	}

	@java.lang.SuppressWarnings("all")
	public Date getCreated() {
		return this.created;
	}

	@java.lang.SuppressWarnings("all")
	public Date getLastModified() {
		return this.lastModified;
	}

	@java.lang.SuppressWarnings("all")
	public NodeType getNodeType() {
		return this.nodeType;
	}

	@java.lang.SuppressWarnings("all")
	public String getUserName() {
		return this.userName;
	}

	@java.lang.SuppressWarnings("all")
	public Map<String, String> getProperties() {
		return this.properties;
	}

	@java.lang.SuppressWarnings("all")
	public void setId(final int id) {
		this.id = id;
	}

	@java.lang.SuppressWarnings("all")
	public void setName(final String name) {
		this.name = name;
	}

	@java.lang.SuppressWarnings("all")
	public void setCreated(final Date created) {
		this.created = created;
	}

	@java.lang.SuppressWarnings("all")
	public void setLastModified(final Date lastModified) {
		this.lastModified = lastModified;
	}

	@java.lang.SuppressWarnings("all")
	public void setNodeType(final NodeType nodeType) {
		this.nodeType = nodeType;
	}

	@java.lang.SuppressWarnings("all")
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	@java.lang.SuppressWarnings("all")
	public void setProperties(final Map<String, String> properties) {
		this.properties = properties;
	}

	@java.lang.SuppressWarnings("all")
	public Node() {
		this.uniqueId = Node.$default$uniqueId();
		this.nodeType = Node.$default$nodeType();
	}

	@java.lang.SuppressWarnings("all")
	public Node(final int id, final String uniqueId, final String name, final Date created, final Date lastModified, final NodeType nodeType, final String userName, final Map<String, String> properties) {
		this.id = id;
		this.uniqueId = uniqueId;
		this.name = name;
		this.created = created;
		this.lastModified = lastModified;
		this.nodeType = nodeType;
		this.userName = userName;
		this.properties = properties;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public java.lang.String toString() {
		return "Node(id=" + this.getId() + ", uniqueId=" + this.getUniqueId() + ", name=" + this.getName() + ", created=" + this.getCreated() + ", lastModified=" + this.getLastModified() + ", nodeType=" + this.getNodeType() + ", userName=" + this.getUserName() + ", properties=" + this.getProperties() + ")";
	}
}
