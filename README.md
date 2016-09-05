[![Build Status](https://travis-ci.org/Blazebit/blaze-weblink.svg?branch=master)](https://travis-ci.org/Blazebit/blaze-weblink)

blaze-weblink
==========
blaze-weblink is an extendible service that allows dispatching to other resources via HTTP

What is it?
===========

blaze-weblink is a service that can be used to dispatch URLs to other resources.
The original intent is to provide a URL shortening and aliasing service with expiration support.
It is possible to use HTTP basic authentication and IP access restrictions for weblinks.

Possible future features are
* Statistics for URLs
* Support for URL-Rewriting instead of aliasing

How to use it?
==============

Currently you have to build it yourself, but soon we will publish the version 1.0 to
the Maven Central.

General idea
============
A WeblinkGroup is a named container that has a key strategy and matcher configured for it's weblinks.
When a matcher decides a WeblinkGroup is appropriate for a request, only Weblinks from that group will be considered.
A key strategy is responsible for generating keys for new weblinks.
Weblinks are mappings to a target URI. A configured dispatcher decides how to dispatch the request to the target.
A Weblink can have an expiration time and also be assigned to a security group.
WeblinkSecurityGroups are named security constraint collections which are attached to weblinks. On dispatch, the constraints are enforced.

 
Licensing
=========

This distribution, as a whole, is licensed under the terms of the Apache
License, Version 2.0 (see LICENSE.txt).

References
==========

Project Site:              http://blazebit.com/weblink (Coming soon)
