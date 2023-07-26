/*
 * Copyright (c) 2016 American Express Travel Related Services Company, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */


package io.aexp.api.client.core.security.authentication;

/**
 * Provides an implementation of AuthProvider for Amex HMAC authentication schema.
 */
public class HmacAuthBuilder extends BaseAuthBuilder {
	
	private HmacAuthProvider provider;
	
	private HmacAuthBuilder(){
		super();
		this.provider = new HmacAuthProvider();
	}
	
	
	public static final HmacAuthBuilder getBuilder(){
		return new HmacAuthBuilder();
	}

	
	public AuthProvider build() {
		HmacAuthProvider provider = new HmacAuthProvider();
		provider.setConfiguration(super.getConfiguration());

		return  provider;
	}


}
