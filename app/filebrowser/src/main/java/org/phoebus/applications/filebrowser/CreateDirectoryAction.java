/*******************************************************************************
 * Copyright (c) 2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.phoebus.applications.filebrowser;

import java.io.File;

import org.phoebus.framework.jobs.JobManager;
import org.phoebus.ui.dialog.DialogHelper;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

/** Menu item to create a new directory
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class CreateDirectoryAction extends MenuItem
{
    /** @param node Node used to position confirmation dialog
     *  @param item Item under which to create a new folder
     */
    public CreateDirectoryAction(final Node node, final TreeItem<File> item)
    {
        super("New Folder", new ImageView(FileTreeCell.folder));

        setOnAction(event ->
        {
            // Prompt for new name
            final File file = item.getValue();
            final TextInputDialog prompt = new TextInputDialog(file.getName());
            prompt.setTitle(getText());
            prompt.setHeaderText("Enter name for new folder under " + item.getValue());
            DialogHelper.positionDialog(prompt, node, 0, 0);
            final String new_name = prompt.showAndWait().orElse(null);
            if (new_name == null)
                return;

            // Abort if folder already exists
            final File new_folder = new File(item.getValue(), new_name);
            if (new_folder.exists())
            {
                final Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle(getText());
                alert.setHeaderText("Folder\n   " + new_folder + "\nalready exists");
                DialogHelper.positionDialog(alert, node, 0, 0);
                alert.showAndWait();
                return;
            }

            // Create in background
            JobManager.schedule(getText(), monitor ->
            {
                if (! new_folder.mkdirs())
                    throw new Exception("Cannot create new folder " + new_folder);

                // Add new folder to tree
                Platform.runLater(() ->
                {
                    final ObservableList<TreeItem<File>> children = item.getChildren();
                    children.add(new FileTreeItem(((FileTreeItem)item).getMonitor(), new_folder));
                    FileTreeItem.sortSiblings(children);
                });
            });
        });
    }
}
