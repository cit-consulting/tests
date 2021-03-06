/**********************************************************************
Copyright (c) 2009 Stefan Seelmann and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


Contributors :
 ...
 ***********************************************************************/
package org.datanucleus.tests.directory.embedded;

import java.util.HashSet;
import java.util.Set;

public class ContactData
{

    private Address address;

    private Set<String> phoneNumbers = new HashSet<String>();

    private Person person;

    public ContactData()
    {
    }

    public ContactData(Address address, Person person)
    {
        super();
        this.address = address;
        this.person = person;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Set<String> getPhoneNumbers()
    {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<String> phoneNumbers)
    {
        this.phoneNumbers = phoneNumbers;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public String toString()
    {
        return "Contact Data of " + person.toString();
    }

}
