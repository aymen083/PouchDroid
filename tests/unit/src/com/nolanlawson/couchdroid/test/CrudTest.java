package com.nolanlawson.couchdroid.test;

import java.util.Random;

import junit.framework.Assert;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;

import com.nolanlawson.couchdroid.appforunittests.MainActivity;
import com.nolanlawson.couchdroid.pouch.PouchDB;
import com.nolanlawson.couchdroid.pouch.SynchronousPouchDB;
import com.nolanlawson.couchdroid.pouch.SynchronousPouchDB.PouchException;
import com.nolanlawson.couchdroid.test.data.Person;

@SuppressLint("NewApi")
public class CrudTest extends ActivityInstrumentationTestCase2<MainActivity>{
    
    public CrudTest() {
        super(MainActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        while (getActivity() == null || getActivity().getCouchDroidRuntime() == null) {
            Thread.sleep(100);
        }
    }



    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testCreateDestroy() throws PouchException {
        String dbName = "unit-test-" + Integer.toHexString(new Random().nextInt());
        
        SynchronousPouchDB<Person> pouchDB = PouchDB.newSynchronousPouchDB(Person.class, 
                getActivity().getCouchDroidRuntime(), dbName);
        
        Person person = new Person("Mr. Mackey", 0, 0, null, false);
        person.setPouchId("fooId");
        pouchDB.put(person);
        Person gotPerson = pouchDB.get("fooId");
        assertEquals(person, gotPerson);
        pouchDB.destroy();
        try {
            pouchDB.get("fooId");
            Assert.fail();
        } catch (PouchException expected) {
            
        }
    }

    public void testPutPostGet() {
        
        
    }

}
