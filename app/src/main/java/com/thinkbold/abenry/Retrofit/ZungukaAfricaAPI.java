package com.thinkbold.abenry.Retrofit;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ZungukaAfricaAPI {

    //method for getting list PHp
    @GET("list.php")
    Call<String>getString();

    //method for sending sending feedback
    @FormUrlEncoded
    @POST("feedback.php")
    Call<ResponseBody>insertFeedback(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("comment") String comment
    );
    //method for sending rating
    @FormUrlEncoded
    @POST("rating.php")
    Observable<String>insertRating(
            @Field("value") String value,
            @Field("scale") String scale,
            @Field("ratingvalue") String ratingvalue,
            @Field("title") String title
    );

    @FormUrlEncoded
    @POST("fcm/newUserToken.php")
    Observable<String> newUserToken(@Field("token") String token
    );

    @FormUrlEncoded
    @POST("getYoutubedata.php")
    Observable<String> getYoutubedata(
            @Field("tmps") String tmps
    );

    @FormUrlEncoded
    @POST("execution_email_usageproblem.php")
    Observable<String> sendEmailUsageProblem(
            @Field("description") String description,
            @Field("usernumberverified") String usernumberverified
    );

    @FormUrlEncoded
    @POST("execution_email_feedback.php")
    Observable<String> sendEmailFeedback(
            @Field("name") String name,
            @Field("email") String imgPath,
            @Field("feedback") String price
    );


    @Multipart
    @POST("upload_product_img.php")
    Call<String> uploadProductFile(@Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("sendFlightBookingEmail.php")
    Observable<String> sendFlightBookingEmail(@Field("name") String name,
                                              @Field("phonenumber") String phonenumber,
                                              @Field("email") String email,
                                              @Field("triptype") String triptype,
                                              @Field("trip_from") String trip_from,
                                              @Field("trip_to") String trip_to,
                                              @Field("depart_date") String depart_date,
                                              @Field("return_date") String return_date,
                                              @Field("adults") String adults,
                                              @Field("children") String children,
                                              @Field("infant") String infant,
                                              @Field("details") String details,
                                              @Field("uploaded_img_path") String uploaded_img_path
    );


    @FormUrlEncoded
    @POST("execution_emailTrainBooking.php")
    Observable<String> sendTrainBookingEmail(@Field("name") String name,
                                             @Field("phonenumber") String phonenumber,
                                             @Field("email") String email,
                                             @Field("triptype") String triptype,
                                             @Field("trip_from") String trip_from,
                                             @Field("trip_to") String trip_to,
                                             @Field("depart_date") String depart_date,
                                             @Field("return_date") String return_date,
                                             @Field("adults") String adults,
                                             @Field("children") String children,
                                             @Field("infant") String infant,
                                             @Field("details") String details,
                                             @Field("uploaded_img_path") String uploaded_img_path
    );

    @FormUrlEncoded
    @POST("newTrainBooking.php")
    Observable<String> newTrainBooking(@Field("name") String name,
                                       @Field("phonenumber") String phonenumber,
                                       @Field("email") String email,
                                       @Field("triptype") String triptype,
                                       @Field("trip_from") String trip_from,
                                       @Field("trip_to") String trip_to,
                                       @Field("depart_date") String depart_date,
                                       @Field("return_date") String return_date,
                                       @Field("adults") String adults,
                                       @Field("children") String children,
                                       @Field("infant") String infant,
                                       @Field("details") String details,
                                       @Field("uploaded_img_path") String uploaded_img_path
    );

    @FormUrlEncoded
    @POST("newFlightBooking.php")
    Observable<String> newFlightBooking(@Field("name") String name,
                                        @Field("phonenumber") String phonenumber,
                                        @Field("email") String email,
                                        @Field("triptype") String triptype,
                                        @Field("trip_from") String trip_from,
                                        @Field("trip_to") String trip_to,
                                        @Field("depart_date") String depart_date,
                                        @Field("return_date") String return_date,
                                        @Field("adults") String adults,
                                        @Field("children") String children,
                                        @Field("infant") String infant,
                                        @Field("details") String details,
                                        @Field("uploaded_img_path") String uploaded_img_path,
                                        @Field("flightType") String flightType
    );

    @FormUrlEncoded
    @POST("getflightsdest.php")
    Observable<String> getFlightsdest(
            @Field("tmps") String tmps,
            @Field("tripType") String tripType
    );

    @FormUrlEncoded
    @POST("gettraindest.php")
    Observable<String> getTraindest(
            @Field("tmps") String tmps
    );


    @FormUrlEncoded
    @POST("execution_email_booking.php")
    Observable<String> sendEmailBooking(
            @Field("name") String name,
            @Field("username") String username,
            @Field("usernumberverified") String usernumberverified,
            @Field("email") String email,
            @Field("country") String adults,
            @Field("details") String kids,
            @Field("itineraryName") String main_interest,
            @Field("booking_ref") String tour_date,
            @Field("pax") String details,
            @Field("totalAmount") String totalAmount
    );

    @FormUrlEncoded
    @POST("execution_email.php")
    Observable<String> sendEmailInquire(@Field("name") String name,
                                        @Field("phonenumber") String phonenumber,
                                        @Field("email") String email,
                                        @Field("adults") String adults,
                                        @Field("kids") String kids,
                                        @Field("main_interest") String main_interest,
                                        @Field("tour_date") String tour_date,
                                        @Field("details") String details,
                                        @Field("selectedBudgetRange") String selectedBudgetRange
    );


    @FormUrlEncoded
    @POST("newBooking.php")
    Observable<String> newBooking(@Field("name") String name,
                                  @Field("email") String email,
                                  @Field("country") String adults,
                                  @Field("details") String kids,
                                  @Field("itineraryName") String main_interest,
                                  @Field("booking_ref") String tour_date,
                                  @Field("pax") String details,
                                  @Field("totalAmount") String totalAmount
    );

    @FormUrlEncoded
    @POST("getCountries.php")
    Observable<String> getCountries(
            @Field("tmps") String tmps
    );


    @FormUrlEncoded
    @POST("newGeneralInquire.php")
    Observable<String> newGeneralInquire(@Field("name") String name,
                                         @Field("phonenumber") String phonenumber,
                                         @Field("email") String email,
                                         @Field("adults") String adults,
                                         @Field("kids") String kids,
                                         @Field("main_interest") String main_interest,
                                         @Field("tour_date") String tour_date,
                                         @Field("details") String details,
                                         @Field("selectedBudgetRange") String selectedBudgetRange
    );

    @FormUrlEncoded
    @POST("checkexists.php")
    Observable<String> CheckUserExists(@Field("usernumberverified") String verified_phonenumber
    );


    @GET("checkAppVersion.php")
    Observable<String> checkAppVersion
            ();

    @FormUrlEncoded
    @POST("addadvertemail.php")
    Observable<String> SendAdvertEmail(@Field("advertEmail") String advertEmail
    );

    @FormUrlEncoded
    @POST("addwishlist.php")
    Observable<String> AddWishList(@Field("attractions_id") String attractions_id,
                                   @Field("attractions_name") String attractions_name,
                                   @Field("userphonenumber") String userphonenumber
    );

    @FormUrlEncoded
    @POST("removeWishlist.php")
    Observable<String> RemoveWishList(@Field("attractions_id") String attractions_id,
                                      @Field("userphonenumber") String verified_phonenumber
    );

    @FormUrlEncoded
    @POST("checkwishlist.php")
    Observable<String> checkIsWishlist(@Field("attractions_id") String attractions_id,
                                       @Field("verified_phonenumber") String verified_phonenumber
    );

    @FormUrlEncoded
    @POST("newcomment.php")
    Observable<String> NewUserComments(@Field("comment") String comment,
                                       @Field("comment_date") String comment_date,
                                       @Field("comment_id") String comment_id,
                                       @Field("post_id") String post_id,
                                       @Field("usernumberverified") String usernumberverified
    );


    @FormUrlEncoded
    @POST("completeRegistration.php")
    Observable<String> NewUserRegistration(@Field("verified_phonenumber") String verified_phonenumber,
                                           @Field("accountName") String accountName,
                                           @Field("userToken") String userToken,
                                           @Field("emailAddress") String emailAddress,
                                           @Field("newsLetterSubscription") String newsLetterSubscription
    );


    @FormUrlEncoded
    @POST("usageproblem.php")
    Observable<String> UsageProblem(@Field("description") String description,
                                    @Field("usernumberverified") String usernumberverified
    );

    @FormUrlEncoded
    @POST("newpost.php")
    Observable<String> uploadPosts(@Field("uniqueId") String uniqueId,
                                   @Field("posturl") String posturl,
                                   @Field("thmbnailurl") String thmbnailurl,
                                   @Field("posttype") String posttype,
                                   @Field("description") String description,
                                   @Field("usernumberverified") String usernumberverified
    );


    @FormUrlEncoded
    @POST("addfeedback.php")
    Observable<String> AddFeedback(@Field("name") String name,
                                   @Field("email") String imgPath,
                                   @Field("feedback") String price
    );


    @FormUrlEncoded
    @POST("addphonenumber.php")
    Observable<String> RegisterNumber(@Field("verified_phonenumber") String verified_phonenumber
    );





}
