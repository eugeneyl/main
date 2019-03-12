package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.address.testutil.TypicalRecords.ALICE;
import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.record.Record;
import seedu.address.model.record.exceptions.DuplicateRecordException;
import seedu.address.testutil.RecordBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getRecordList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateRecords_throwsDuplicateRecordException() {
        // Two records with the same identity fields
        Record editedAlice = new RecordBuilder(ALICE).withAmount(VALID_AMOUNT_BOB)
                .withCategories(VALID_CATEGORY_HUSBAND)
                .build();
        List<Record> newRecords = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newRecords);

        thrown.expect(DuplicateRecordException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasRecord(ALICE));
    }

    @Test
    public void hasRecord_recordInAddressBook_returnsTrue() {
        addressBook.addRecord(ALICE);
        assertTrue(addressBook.hasRecord(ALICE));
    }

    @Test
    public void hasRecord_recordWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addRecord(ALICE);
        Record editedAlice = new RecordBuilder(ALICE).withAmount(VALID_AMOUNT_BOB)
                .withCategories(VALID_CATEGORY_HUSBAND)
                .build();
        assertTrue(addressBook.hasRecord(editedAlice));
    }

    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getRecordList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.addRecord(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.removeListener(listener);
        addressBook.addRecord(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyAddressBook whose records list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Record> records = FXCollections.observableArrayList();

        AddressBookStub(Collection<Record> records) {
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return records;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
