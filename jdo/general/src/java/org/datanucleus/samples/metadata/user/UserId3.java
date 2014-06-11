/**********************************************************************
Copyright (c) Jul 14, 2004 Erik Bengtson and others.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 

Contributors:
    ...
**********************************************************************/
package org.datanucleus.samples.metadata.user;

/**
 * @author Erik Bengtson
 * @version $Revision: 1.1 $
 */
public class UserId3 implements java.io.Serializable
{
	private static final long serialVersionUID = -7181813044434993324L;
    public String id;

	/**
	 *	Default constructor.
	 */
	public UserId3 ()
	{
	}

	/**
	 *	String constructor.
	 */
	public UserId3 (String str) 
	{
		this.id = str;
	}

	/**
	 *	Implementation of equals method.
	 */
	public boolean equals (Object ob)
	{
		if (this == ob)
			return true;
		if (!(ob instanceof UserId3))
			return false;

		UserId3 other = (UserId3) ob;
		return ((this.id.equals(other.id)) );
	}

	/**
	 *	Implementation of hashCode method that supports the
	 *	equals-hashCode contract.
	 */
	public int hashCode ()
	{
        if (id != null)
        {
		    return id.hashCode();
        }
        return 0;
	}

	/**
	 *	Implementation of toString that outputs this object id's
	 *	primary key values. 
	 */
	public String toString ()
	{
		return this.id;
	}

}
