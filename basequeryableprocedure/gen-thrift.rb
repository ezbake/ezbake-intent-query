#!/usr/bin/env ruby
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


require 'fileutils'
require 'yaml'

if `thrift -version` !~ /0.9.1/
    puts 'Thrift 0.9.1 is not on the path!'
    exit
end

script_dir = File.expand_path(File.dirname(__FILE__))
services_thrift = script_dir

DEP_DIRS = ["#{services_thrift}/ezbake-thrift", "#{services_thrift}/intent-thrift"]
SUBMODULE_DIRS = DEP_DIRS

SUBMODULE_DIRS.each do |d|
    unless File.directory?(d)
        abort "Thrift dir #{d} does not exist. Aborting..."
    end

    if `git submodule status -- #{d}`.start_with?('-') then
        abort "Thrift dir #{d} is not checked out. Aborting..."
    end
end

FileUtils.rm_rf(["#{services_thrift}/src/main/java"])

includes = DEP_DIRS.map {|d| "-I #{d}/src/main/thrift"}.join(' ')
Dir["#{services_thrift}/src/main/thrift/*.thrift"].each do |thrift_file|
    output = `thrift #{includes} -o #{services_thrift}/src/main --gen java #{thrift_file} 2>&1`
    if output =~ /ERROR|unusable|no/
        $stderr.puts "Error generating thrift file #{thrift_file} (#{output})!"
    else
        puts "Generated code for #{thrift_file} successfully!"
    end
end

FileUtils.cp_r("#{services_thrift}/src/main/gen-java/.", "#{services_thrift}/src/main/java")
FileUtils.rm_rf("#{services_thrift}/src/main/gen-java/")


