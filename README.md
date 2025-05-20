

https://github.com/user-attachments/assets/b3a620f5-6253-4316-b32b-736b6e570e0b



# Vert.x 5 Website Example using Freemarker Templates, PostgreSQL, and Bootstrap 

This is an upgraded project from Vert.x 3

The Freemarker Template gives the platform desirable capability and offloads much of the work much like Typescript. Templates are reusable. We may switch from Freemarker for better performance in Vert.x 5 and or move to Typescript.

    - The include statements are impacted significantly, this may be a correction on our end. More to follow.
    - The templates provide population of the meta in the page heads. This is important.

## OAuth2
The example also uses OAuth2 for logging in with Linkedin. There will be further examples later. (Please note that at the 
present time, this implementation is NOT completed.)

## Password protection
Env files are vulnerable particularly if a developer pushes them to Github. OAuth2, database, and AWS Mail etc need usernames and passwords. Keeping these in the project is problematic in that they are visible to anyone that has access to the repository. Many developers have been a victim of this problem. This project provides a means to store the secrets in protected files.  Other developers can then use the project without the actual passwords. -In reality, this is only an abstraction. Do NOT push these files if the project is public. The only protection this provides is preventing vulnerabilities of env files or config files. 

    
## Prerequisits:
Users should be familiar with Asynchronous Concepts. Vert.x provides a good understanding if you are already familiar with it. They also provide a good book on the subject. The source code in the book is from 2020 but it provides a comprehensive understanding of how Vert.x works. 

Users should be very familiar with Java, HTML, CSS, Javascript, and PostgreSQL.

## Getting started: 

1. After branching the project and mporting it to your IDE, ensure the setup in Maven is correct. The POM file is current as of May 2025. Rename the project in the POM file. Update the dependencies. The OWASP dependency checker will underline dependencies that are vulnerable.
   
3. Create a directory on your desktop `/VertxServerSecrets/ServerSecrets.properties` .This is the directory and file that is expected to be present. Store the project passwords this .properties file.
   
    -- Go to the ` example.web.access.SecretEncDec `. Uncomment the Main Method. This will encrypt the ` .properties file ` and store it in the project. The instructions to run the class are provided within the class.

4. The index page will need to have the images replaced. There is one image used as a placeholder so the page will run.     

5. Create a database: You will need 3 tables for the blog. Blogs, BlogRow, and BlogResource. Blogs primarily stores the metadata and other organization information. BlogRows is the data within the paragraphs and formatting/structure information, and BLogRefs is information that would be used as a reference for the blog. BlogRefs is optional.

Schema: 
```
CREATE SEQUENCE blogs_id_seq;
create table blogs
(
    blog_id            bigint  default nextval('blogs_id_seq'::regclass) not null primary key,
    article            boolean default true,
    title              varchar                                                not null,
    read_time          integer                                                not null,
    meta_descript      varchar                                                not null,
    card_intro         varchar                                                not null,
    author             varchar                                                not null,
    create_date        date    default CURRENT_DATE,
    change_date        date,
    change_descript    varchar,
    page_endpoint      varchar                                                not null,
    image_link         varchar                                                not null,
    image_card_link    varchar                                                not null,
    image_card_alt     varchar,
    image_twitter_link varchar                                                not null
);

create table Blog_row
(
    blog_id            bigint  not null references blogs,
    intro              boolean default false,
    new_row            boolean default false,
    section_left       boolean default true,
    section_left_end   boolean default false,
    subtitle           boolean default false,
    num_list_start     boolean default false,
    num_start_no       integer default 1,
    num_list_end       boolean default false,
    num_list_item      boolean default false,
    para_num           integer not null,
    h2_h3              varchar default 'h2'::character varying,
    h2_subtitle        varchar,
    para               varchar,
    img                boolean default false,
    img_name           varchar,
    img_alt            varchar,
    img_sz             integer default 7,
    unorder_list_start boolean default false,
    unorder_list_end   boolean default false,
    unorder_list_item  boolean default false,
    primary key (blog_id, para_num)
);

create table Blog_resource
(
    blog_id   bigint references blogs,
    reference varchar
);

```

Explanation: This blog is more of an article format than a blog format. It is a complex formatting system that allows 2 side by side columns. Images, A title, an introduction and references. This is more for formal information written from primary source information such as what would be expected in the academic world. Most of the complexity is handled in the ` blog_row `table along with the paragraph, title, and image data. Blog table provides the metadata used in the pages which helps with SEO. It is also used in the blogs listing page or ` all_blogs `. 

` blog_rows ` provides multiple configuration possibilities for a paragraph. The columns include a title, the article page alignment it on the left side, is there an image, and is it the end of the section. Each paragraph is a tuple. It also includes a title and if the title is h2 or h3. Uploading and debugging the format is a manual process but the result is very pleasing. 

Next, is an example of a few paragraphs of data for use in the example project. Note that you will need to provide images and the location if you want the images to show up. Without the images, the pages may not work. If you desire to leave out the images then comment out the images in the templates. I recommend for the first few blogs, keep the images in the webroot resources to make it easier for debugging. Then move the images to your cloud of preference.

Shown below will populate an article format. It contains an introduction, one page with two columns, and a closing. There should be an image at the top, the main title, the intro paragraph. The page should have 4 paragraphs. 2 on the left and two on the right.

    First we insert into the blogs table. This data will also populate the ` all_blogs ` page.

```aiignore
INSERT INTO public.blogs (article, title, read_time, meta_descript, card_intro, author, page_endpoint, image_link, image_card_link, image_card_alt, image_twitter_link)
    VALUES (
            true,
            'Understanding The MyVICI: Meaning, Importance and Implications',
            28,
            'This is the meta description. Keep it short. It is what search engines and social media looks for. It will be populated in the pages meta.',
            'This is the card introduction area. It is in the All Blogs page and is shown as the intro. It can be a little longer. but keep it short.',
            'Author who wrote this',
            'understanding-the-myvici-meaning-importance-and-implications', // Use the title for page link. Must be unique
            'https://us-west-2.console.aws.amazon.com/s3/object/myS3Bucket?region=us-west-2&bucketType=general&prefix=the_blog_image_name.png',
            'https://us-west-2.console.aws.amazon.com/s3/object/myS3Bucket?region=us-west-2&bucketType=general&prefix=a_square_image_for_the_card.png',
            'The image alt name',
            'https://us-west-2.console.aws.amazon.com/s3/object/myS3Bucket?region=us-west-2&bucketType=general&prefix=twitter_photo_for_this_blog.png');

```

    Next we populate the blog_rows table. We start with blog 1, paragraph 0. It's important to keep order correct using paragraph numbers. 

```aiignore

-- The first paragraph, usually an intro. Intro's use both columns. There can be multiple paragraphs in the intro. Indicate that it's part of the intro with true.
INSERT INTO public.knocblog_row (blog_id, intro, new_row, section_left, subtitle, h2_h3, h2_subtitle, num_list_start, num_list_item, para_num, para)
VALUES (
            1,
            true,   
            false, 
            false,
            false,
            'h3',
            'Strategies to Stand Out',
            false,
            false,
            0,      
            'This is an introduction paragraph. A good intro highlights what the article covers and why the reader should read it. Here we will talk about lorum ipsum and why it is used. There are some interesting facts that you will want to read. It isn't just random text.'
      );
      
      
-- The first paragraph after the intro area.  It uses the left column. A column and the intro should be the height of a common device depending on your users preferences. The following sections of left and right columns should follow the height. If a paragraph is too long, split it so that the rest of the paragraph contines in the right column, or in the next section. If the left paragraph is the last paragraph for the left side, indicate in the left_section_end column with true. Here we are showing a simple implementation, we expect the section to end after this text.
     
INSERT INTO public.knocblog_row (blog_id, intro, section_left, section_left_end, subtitle, h2_subtitle, para_num, para)
VALUES (
           1,
           false,
           true,
           false,
           true,
           'What is Lorem Ipsum?',
           1,
           'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. '
       );    
       
       
-- The third paragraph and second on the left, assuming that it is the correct height for the section. This is the last paragraph on the left

INSERT INTO public.knocblog_row (blog_id, intro, section_left, section_left_end, subtitle, h2_subtitle, para_num, para)
VALUES (
           1,
           false,
           true,
           true,
           true,
           'Where does it come from?',
           2,
           'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.'
       );      
         
       
-- The fourth paragraph is the first paragraph on the right. There is not a title for this paragraph.       

INSERT INTO public.knocblog_row (blog_id, intro, new_row, section_left, section_left_end, para_num, para)
VALUES (
           1,
           false,
           false,
           false,
           false,
           3,
           'The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.'
      );   
      
      
-- The fifth paragraph and the last paragraph on the right. This is a simple paragraph with a title.       
          
INSERT INTO public.knocblog_row (blog_id, intro, new_row, section_left, section_left_end, h2_h3, subtitle, h2_subtitle, para_num, para)
VALUES (
           1,
           false,
           false,
           false,
           false,
            'h3',
            true,
            'Why do we use it?',
           4,
           'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like). '
       );   
          
       
-- For the sixth paragraph were showing a conclusion or summary. This paragraph also starts a new section

INSERT INTO public.knocblog_row (blog_id, intro, new_row, section_left, section_left_end, subtitle, h2_subtitle, para_num, para)
VALUES (
           1,
           false,
           true,
           true,
           true,
           true,
           'For the conclusion, we provide an example from Cicero in 45 BC',
           5,
           'At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.'
       );
       
       
-- The final paragraph on the right side. 

INSERT INTO public.knocblog_row (blog_id, intro, new_row, section_left, section_left_end, h2_h3, subtitle, h2_subtitle, para_num, para)
VALUES (
           1,
           false,
           false,
           false,
           false,
            'h3',
            true,
            '1914 translation by H. Rackham',
           6,
           'On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.'
       );            
         

```

### Lists and images

` blog_row ` also provides for images, unordered lists, ordered lists. 

### Block Quotes

A paragraph may be a block quote simply by adding it in the text. `<blockquote>... your block quote here </blockquote>`

## Using Freemarker Templates
The BlogListing table and methods ` PageHandler(...) ` populates a Freemarker Template. The template is located at `resources.webroot.templates.bloglisting.ftl`. FreeMarker makes it simple to provide a complex structure without repeating the html. 

## Notes on column names

If you makes changes, the ` blogPageHandler(...) ` method will output the table data using the same names as the columns. The ` article_template.ftl ` and ` blog_template.ftl ` are expecting the variable names as they are provided in the table as Column Names. But it is not automatic, you must ensure your column names match the template or you will get an error. You can adopt new column names, and make changes to the queries without changes to the ` blogPageHandler(...) `. The ` blogsAllPageHandler(...) ` will require reconfiguration as well since it serializes the returned data from the table into json manually. Each method is commented to hopefully provide an explanation for the experianced developer/SWE.

## Notes on the structure

The Vert.x Server's logic is under java. resources contains the front end logic, webpage etc... It also holds the queries properties and logger configuration.

### SQL LOGIC

We've removed the convienient use of the Database Verticle. We do not see it mentioned except in the "Gentle Introduction to Asynchronous ..." that was introduced in earlier versions of Vert.x. We've also removed use the Database Service but we've adopted some of the logic that made it convenient to develop. 

The database logic can be found under the `database` package. The ` DataBaseHandler ` class contains most of the top level logic for the queries. The SqlQuery Enum Class is for convenience. We divide queries into separate classes between Select, Upsert, and common Operations. The actual queries are written as any SQL query using a tool that helps us write, test, and optimize it. We can copy the query directly into a properties file and modify it using ` $1,  $2 ` etc to get the data from the params. This is convenient. We then place the queries in a map held in the appropriate Query Class. 

## Next

Future versions should include:

    1. Full implemenation of the OAuth2 and OpenID login with important providers such as Linkedin and Google. 
    2. Instrumentation and metrics so we understand site performance and server performance.
    3. Moving to an SPA.

## Going Further: 

Vert.x is a toolset to build high performance servers. It offers many advantages that make it an excellent choice for startups. The highest priority being that it saves cloud costs by requiring less compute. To understand more about building Vert.x servers check out their site at ` https://vertx.io/. ` It is a mature platform and is well supported by a community. It has several big players using it including Hulu and Groupon. They provide many examples of how to implement their services. 

Also, to understand Vert.x read the book. It was writtin in 2020ish so much of the implementation information is more informational. They also have the "A Gentle Guide to Asynchronous Programming Using Vert.x" which provides some implementation in an integrated top level understanding. It was written for version 3. It's an old implemenation with great performance, but lacks the hooks needed for modern instrumentation, OAuth, etc...


## Resources: 
I recommend these sources:
### OAuth2 and OpenID integration with Vert.x 5

    - The examples repo, Testing sometimes has good hints
    https://github.com/eclipse-vertx/vertx-auth/blob/master/vertx-auth-oauth2/src/main/java/examples/AuthOAuth2Examples.java
    
    - Some information
    https://vertx.io/docs/howtos/web-and-oauth2-oidc/

    - Notes from migration
    https://docs.redhat.com/en/documentation/red_hat_build_of_eclipse_vert.x/4.3/html/eclipse_vert.x_4.3_migration_guide/authentication-and-authorization_vertx#deprecated-and-removed-authentication-and-authorization-methods_authentication-and-authorization

    - Considerations for login security
    https://www.reddit.com/r/devops/comments/1ak7pex/is_keycloak_worth_the_maintenance/
    








