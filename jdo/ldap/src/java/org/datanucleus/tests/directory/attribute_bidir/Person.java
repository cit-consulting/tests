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
package org.datanucleus.tests.directory.attribute_bidir;

import java.util.HashSet;
import java.util.Set;

public class Person
{

    private String fullName;

    private String firstName;

    private String lastName;

    private Department department;

    private Address address;

    private Computer computer;

    private Set<Account> accounts = new HashSet<Account>();

    private Set<SecurityGroup> groups = new HashSet<SecurityGroup>();

    private Set<Project> projects = new HashSet<Project>();

    public Person()
    {
    }

    public Person(String firstName, String lastName, String fullName, Department department, Address address, Computer computer)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.department = department;
        this.address = address;
        this.computer = computer;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Computer getComputer()
    {
        return computer;
    }

    public void setComputer(Computer computer)
    {
        this.computer = computer;
    }

    public Set<Account> getAccounts()
    {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts)
    {
        this.accounts = accounts;
    }

    public Set<SecurityGroup> getGroups()
    {
        return groups;
    }

    public void setGroups(Set<SecurityGroup> groups)
    {
        this.groups = groups;
    }

    public Set<Project> getProjects()
    {
        return projects;
    }

    public void setProjects(Set<Project> projects)
    {
        this.projects = projects;
    }

    public String toString()
    {
        return getFullName();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (fullName == null)
        {
            if (other.fullName != null)
                return false;
        }
        else if (!fullName.equals(other.fullName))
            return false;
        return true;
    }

}
