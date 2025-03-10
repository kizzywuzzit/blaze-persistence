/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */

package com.blazebit.persistence.view.impl.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Christian Beikov
 * @since 1.2.0
 */
public final class CollectionOperations {
    private final List<? extends CollectionAction<? extends Collection<?>>> actions;
    private final CollectionRemoveAllAction removeAction;
    private final CollectionAddAllAction addAction;
    private final int removeIndex;

    public CollectionOperations(List<? extends CollectionAction<? extends Collection<?>>> actions) {
        // We ensure there will always be at most a single remove and add action after a non-add/remove operation
        CollectionRemoveAllAction removeAction = null;
        CollectionAddAllAction addAction = null;
        int removeIndex = actions.size();
        if (!actions.isEmpty()) {
            CollectionAction<?> a = actions.get(actions.size() - 1);
            if (a instanceof CollectionRemoveAllAction) {
                removeAction = (CollectionRemoveAllAction) a;
                removeIndex = actions.size() - 1;
            } else if (a instanceof CollectionAddAllAction) {
                addAction = (CollectionAddAllAction) a;
                removeIndex = actions.size() - 1;
                if (actions.size() > 1) {
                    a = actions.get(actions.size() - 2);
                    if (a instanceof CollectionRemoveAllAction) {
                        removeAction = (CollectionRemoveAllAction) a;
                        removeIndex = actions.size() - 2;
                    }
                }
            }
        }

        // We ensure there will always be at most a single remove and add action after a non-add/remove operation
        this.actions = actions;
        this.removeAction = removeAction;
        this.addAction = addAction;
        this.removeIndex = removeIndex;
    }

    public boolean addElements(RecordingCollection<?, ?> recordingCollection, Collection<Object> addedElements) {
        if (!addedElements.isEmpty()) {
            Collection<Object> objectsToAdd = addedElements;
            // Elide removed elements for newly added elements
            if (removeAction != null) {
                objectsToAdd = removeAction.onAddObjects(objectsToAdd);
            }
            // Merge newly added elements into existing add action
            if (!objectsToAdd.isEmpty() && addAction != null) {
                addAction.onAddObjects(objectsToAdd);
                return false;
            } else {
                if (addedElements != objectsToAdd) {
                    Iterator<Object> iterator = addedElements.iterator();
                    while (iterator.hasNext()) {
                        Object o = iterator.next();
                        if (!objectsToAdd.contains(o)) {
                            iterator.remove();
                            recordingCollection.addAddedElement(o);
                        }
                    }
                }
                return !objectsToAdd.isEmpty();
            }
        }

        return false;
    }

    public int removeElements(RecordingCollection<?, ?> recordingCollection, Collection<Object> removedElements) {
        if (!removedElements.isEmpty()) {
            Collection<Object> objectsToRemove = removedElements;
            // Elide added elements for newly removed elements
            if (addAction != null) {
                objectsToRemove = addAction.onRemoveObjects(objectsToRemove);
            }
            if (!objectsToRemove.isEmpty()) {
                // Merge newly removed elements into existing remove action
                if (removeAction != null) {
                    removeAction.onRemoveObjects(objectsToRemove);
                } else {
                    if (removedElements != objectsToRemove) {
                        Iterator<Object> iterator = removedElements.iterator();
                        while (iterator.hasNext()) {
                            Object o = iterator.next();
                            if (!objectsToRemove.contains(o)) {
                                iterator.remove();
                                recordingCollection.addRemovedElement(o);
                            }
                        }
                    }
                    return removeIndex;
                }
            }
        }

        return -1;
    }

    public void removeEmpty() {
        if (addAction != null && addAction.isEmpty()) {
            actions.remove(actions.size() - 1);
        }
        if (removeAction != null && removeAction.isEmpty()) {
            actions.remove(removeIndex);
        }
    }
}
