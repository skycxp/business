/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.product;

import org.seedstack.business.domain.BaseFactory;

import javax.inject.Inject;

public class InternalProductFactory extends BaseFactory<Product> implements ProductFactory {
	
	@Inject 
	ProductNamePolicy productNamePolicy;
	
	public InternalProductFactory() {
	}

	@Override
	public Product createProduct(Short storeId, Short productCode ) {
		ProductId id = new ProductId ( storeId,  (String) productNamePolicy.transform (productCode) );
		Product product = new Product(id);
		
		return product;
	}
	
	@Override
	public Product createProduct(Short storeId , Short productCode , String name , String description)
	{
		Product product = createProduct(storeId, productCode);
		product.setName(name);
		product.setDescription(description);
		
		return product;
	}

}
