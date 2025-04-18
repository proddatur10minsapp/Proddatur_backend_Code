In UserController :
    CommonAPI=/user/authentication
    APIs :
        /generate   = Used to generate otp through mobileNumber.
        /save       = Used to validate the otp based on mobileNumber & to save the user Details.
        /updateUser = Used to update user Details through mobileNumber.
        
In ProductAdminController :
    CommonAPi = /admin/products
    APIs :
        /add            = used to add product.
        /addMultiple    = used to add multiple products.
        /getAllProducts = used to get all products.
        /update/{id}    = used to update the product via id.
        /delete/{id}    = used to delete the product via id.

 In ProductUserController :
     CommonAPi = /users/products
     APIs :
         /getProducts/{category}                  = used to get first 20 products.
         /products/{id}                           = used to get products via id.
         /getAllProducts/{category}               = used to get all products via category.
         /getMoreProducts/{category}/{nextValues} = used to get products via category and indexes.
