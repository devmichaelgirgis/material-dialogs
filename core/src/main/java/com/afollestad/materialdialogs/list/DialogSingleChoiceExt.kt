/*
 * Licensed under Apache-2.0
 *
 * Designed and developed by Aidan Follestad (@afollestad)
 */
@file:Suppress("unused")

package com.afollestad.materialdialogs.list

import androidx.annotation.ArrayRes
import androidx.annotation.CheckResult
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton.POSITIVE
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.assertOneSet
import com.afollestad.materialdialogs.internal.list.DialogAdapter
import com.afollestad.materialdialogs.internal.list.SingleChoiceDialogAdapter
import com.afollestad.materialdialogs.utils.getStringArray

/**
 * @param res The string array resource to populate the list with.
 * @param items The literal string array to populate the list with.
 * @param initialSelection The initially selected item's index.
 * @param waitForPositiveButton When true, the [selection] listener won't be called until
 *    the positive action button is pressed. Defaults to true if the dialog has buttons.
 * @param selection A listener invoked when an item in the list is selected.
 */
@CheckResult fun MaterialDialog.listItemsSingleChoice(
  @ArrayRes res: Int? = null,
  items: List<String>? = null,
  disabledIndices: IntArray? = null,
  initialSelection: Int = -1,
  waitForPositiveButton: Boolean = true,
  selection: SingleChoiceListener = null
): MaterialDialog {
  val array = items ?: getStringArray(res)?.toList() ?: return this
  val adapter = getListAdapter()

  if (adapter is SingleChoiceDialogAdapter) {
    adapter.replaceItems(array, selection)
    if (disabledIndices != null) {
      adapter.disableItems(disabledIndices)
    }
    return this
  }

  assertOneSet("listItemsSingleChoice", items, res)
  setActionButtonEnabled(POSITIVE, initialSelection > -1)
  return customListAdapter(
      SingleChoiceDialogAdapter(
          dialog = this,
          items = array,
          disabledItems = disabledIndices,
          initialSelection = initialSelection,
          waitForActionButton = waitForPositiveButton,
          selection = selection
      )
  )
}

/** Checks a single or multiple choice list item. */
fun MaterialDialog.checkItem(index: Int) {
  val adapter = getListAdapter()
  if (adapter is DialogAdapter<*, *>) {
    adapter.checkItems(intArrayOf(index))
    return
  }
  throw UnsupportedOperationException(
      "Can't check item on adapter: ${adapter?.javaClass?.name ?: "null"}"
  )
}

/** Unchecks a single or multiple choice list item. */
fun MaterialDialog.uncheckItem(index: Int) {
  val adapter = getListAdapter()
  if (adapter is DialogAdapter<*, *>) {
    adapter.uncheckItems(intArrayOf(index))
    return
  }
  throw UnsupportedOperationException(
      "Can't uncheck item on adapter: ${adapter?.javaClass?.name ?: "null"}"
  )
}

/** Checks or unchecks a single or multiple choice list item. */
fun MaterialDialog.toggleItemChecked(index: Int) {
  val adapter = getListAdapter()
  if (adapter is DialogAdapter<*, *>) {
    adapter.toggleItems(intArrayOf(index))
    return
  }
  throw UnsupportedOperationException(
      "Can't toggle checked item on adapter: ${adapter?.javaClass?.name ?: "null"}"
  )
}

/** Returns true if a single or multiple list item is checked. */
fun MaterialDialog.isItemChecked(index: Int): Boolean {
  val adapter = getListAdapter()
  if (adapter is DialogAdapter<*, *>) {
    return adapter.isItemChecked(index)
  }
  throw UnsupportedOperationException(
      "Can't check if item is checked on adapter: ${adapter?.javaClass?.name ?: "null"}"
  )
}
