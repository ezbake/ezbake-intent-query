#   Copyright (C) 2013-2014 Computer Sciences Corporation
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

import sys
import os
import subprocess
import glob
import errno

from util.properties.Properties import Properties

def copyfile(source, dest, buffer_size=1024*1024):
    """
    Copy a file from source to dest. source and dest
    can either be strings or any object with a read or
    write method, like StringIO for example.
    """
    if not hasattr(source, 'read'):
        source = open(source, 'rb')
    if not hasattr(dest, 'write'):
        dest = open(dest, 'wb')

    while 1:
        copy_buffer = source.read(buffer_size)
        if copy_buffer:
            dest.write(copy_buffer)
        else:
            break

    source.close()
    dest.close()
    
def make_sure_path_exists(path):
    try:
        os.makedirs(path)
    except OSError as exception:
        if exception.errno != errno.EEXIST:
            raise
    
def update_ezbake_properties(key,value,dataset,configfile):
    
    cwd = os.getcwd()
    props = Properties()
    props.load(open(configfile))
    os.chdir("../" + dataset + "/src/main/resources/")
    ezbakeconfigfilepath = os.path.abspath(value)
    
    print("CWD: " + os.getcwd())
    
    props.setProperty(key,ezbakeconfigfilepath)
    
    os.chdir(cwd)
    props.store(open(configfile,'w'))
     
    
if __name__=="__main__":

    """ length of the command line arguments, should be 2 """
    ezconfigfile = "ezbake-config.properties"
    ezconfigdir = os.getcwd()
        
    """ mvn clean install to download ezbake thrift runner jar from artifactory """
    """ subprocess.call("mvn clean install", shell = True) """
    	
    thriftrunnerfile = glob.glob("./target/ezbake-thrift-runner-*")
    
    """ throw error and return if more than one ezbake-thrift-runner files exist """
    if len(thriftrunnerfile) > 1:
        print("Error: More than one ezbake-thrift-runner file.")
        sys.exit()
        
    print("Ezbake Thrift Runner : " + thriftrunnerfile[0])
    """ thrift runner which is downloaded from artifactory """
    RUNNER = thriftrunnerfile[0]
    
    print ("EzConfiguration Dir : " + ezconfigdir)
    """set ezconfiguration environment variable""" 
    os.environ['EZCONFIGURATION_DIR'] = ezconfigdir
    
    print("EZCONF DIR : " + os.environ['EZCONFIGURATION_DIR'])
    
    
    java_command = "java -jar " + RUNNER + " -j " + "../target/IntentQueryThriftService-1.0-SNAPSHOT.jar" \
                                + " -s intentquery -a testapp -D ezbake.log.directory=./log"
                                
    print(java_command)
    
    subprocess.call(java_command, shell = True)
