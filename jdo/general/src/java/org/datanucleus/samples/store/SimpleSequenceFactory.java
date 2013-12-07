/**********************************************************************
Copyright (c) 2005 Andy Jefferson and others. All rights reserved.
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
package org.datanucleus.samples.store;

import javax.jdo.datastore.Sequence;

/**
 * Example of a factory class for a sequence
 * @version $Revision: 1.1 $
 */
public class SimpleSequenceFactory
{
    public static Sequence newInstance(String name)
    {
        return new SimpleSequence(name);
    }

    public static Sequence newInstance(String name, String strategy)
    {
        return new SimpleSequence(name);
    }
}