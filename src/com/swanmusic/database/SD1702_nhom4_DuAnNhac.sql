USE MASTER
GO

DROP DATABASE SWAN
GO

CREATE DATABASE SWAN
GO

USE SWAN
GO

CREATE TABLE TAIKHOAN(
	TENTAIKHOAN VARCHAR(20) PRIMARY KEY NOT NULL,
	MATKHAU VARCHAR(20) NOT NULL,
	VAITRO BIT NOT NULL, -- 1. ADMIN , 0. USER
	EMAIL VARCHAR(30) NOT NULL,
	SODIENTHOAI VARCHAR(11)
);

CREATE TABLE NGHESI(
	TENNGHESI NVARCHAR(50) PRIMARY KEY NOT NULL,
	SL_ALBUM  INT NOT NULL,
	SL_DSNHAC INT NOT NULL,
	ANH VARCHAR(50)
);

CREATE TABLE THELOAI(
	ID INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	TENTL NVARCHAR(20) NOT NULL,
	UNIQUE(TENTL)
);

CREATE TABLE ALBUM(
	TENALBUM NVARCHAR(50) PRIMARY KEY NOT NULL,
	NGHESI NVARCHAR(50) FOREIGN KEY REFERENCES NGHESI(TENNGHESI) NOT NULL,
	THELOAI NVARCHAR(20) FOREIGN KEY REFERENCES THELOAI(TENTL) NOT NULL,
	TG_PHATHANH DATETIME NOT NULL,
	ANH VARCHAR(50)
);

CREATE TABLE NHAC(
	TENNHAC NVARCHAR(50) PRIMARY KEY NOT NULL,
	THELOAI NVARCHAR(20) FOREIGN KEY REFERENCES THELOAI(TENTL) NOT NULL,
	ALBUM NVARCHAR(50) FOREIGN KEY REFERENCES ALBUM(TENALBUM) NOT NULL,
	NGHESI NVARCHAR(50) FOREIGN KEY REFERENCES NGHESI(TENNGHESI) NOT NULL,
	THOILUONG VARCHAR(20),
	LOIBAIHAT NVARCHAR(MAX),
	ANH VARCHAR(50),
	LUOTXEM INT
);

CREATE TABLE USER_PLAYLIST
(
	TENPLAYLIST NVARCHAR(50) NOT NULL,
	TENNHAC NVARCHAR(50) REFERENCES NHAC(TENNHAC) NOT NULL,
)

CREATE TABLE YEUTHICH(
	TENTAIKHOAN VARCHAR(20) FOREIGN KEY REFERENCES TAIKHOAN(TENTAIKHOAN) NOT NULL,
	NHACYEUTHICH NVARCHAR(50) FOREIGN KEY REFERENCES NHAC(TENNHAC),
	ALBUM NVARCHAR(50) FOREIGN KEY REFERENCES ALBUM(TENALBUM),
	PRIMARY KEY(TENTAIKHOAN)
);



INSERT INTO TAIKHOAN VALUES('PHUONGUYEN', 'admin', '1', 'ADMIN@GMAIL.COM', '0123456789');
INSERT INTO TAIKHOAN VALUES('USER', 'NGUOIDUNG123', '0', 'USER@GMAIL.COM', '0123456788');
INSERT INTO TAIKHOAN VALUES('USER123', 'NGUOIDUNG456', '0', 'USER2@GMAIL.COM', '012345655');

INSERT INTO NGHESI VALUES ('W/N','3','3','');
INSERT INTO NGHESI VALUES ('LINH KA','1','2','');
INSERT INTO NGHESI VALUES (N'VŨ CÁT TƯỜNG','3','2','');
INSERT INTO NGHESI VALUES ('AMEE','6','6','');
INSERT INTO NGHESI VALUES (N'ĐỨC PHÚC','10','9','');
INSERT INTO NGHESI VALUES (N'SƠN TÙNG','5','9','');
INSERT INTO NGHESI VALUES (N'VŨ','7','2','');
INSERT INTO NGHESI VALUES (N'TĂNG DUY TÂN','1','5','');
INSERT INTO NGHESI VALUES (N'NOO PHƯỚC THỊNH','2','9','');
INSERT INTO NGHESI VALUES('DOECHII',2 , 4 , '')
INSERT INTO NGHESI VALUES('MASEGO',2 , 4 , '')
INSERT INTO NGHESI VALUES('NXWORRIES',2 , 4 , '')
INSERT INTO NGHESI VALUES('MUSIQ SOULCHILD',2 , 4 , '')
INSERT INTO NGHESI VALUES('JUSTIN BIEBER',2 , 4 , '')
INSERT INTO NGHESI VALUES('MONO',2 , 4 , '')
INSERT INTO NGHESI VALUES('DANIEL CAESER',2 , 4 , '')
INSERT INTO NGHESI VALUES('MARIO',2 , 4 , '')
INSERT INTO NGHESI VALUES('ELLA MAI',2 , 4 , '')
INSERT INTO NGHESI VALUES('6LACK',2 , 4 , '')
INSERT INTO NGHESI VALUES('MIGUEL',2 , 4 , '')
INSERT INTO NGHESI VALUES('ALEX VAUGHN',2 , 4 , '')
INSERT INTO NGHESI VALUES('NEWJEANS',2 , 4 , '')
INSERT INTO NGHESI VALUES('ARI LENNOX',2 , 4 , '')
INSERT INTO NGHESI VALUES('SZA',2 , 4 , '')
INSERT INTO NGHESI VALUES('THE WEEKND',2 , 4 , '')
INSERT INTO NGHESI VALUES('SNOH AELEGRA',2 , 4 , '')
INSERT INTO NGHESI VALUES('BILLIE EILISH',2 , 4 , '')


INSERT INTO THELOAI VALUES('RAP');
INSERT INTO THELOAI VALUES('K-POP');
INSERT INTO THELOAI VALUES('HIP-HOP');
INSERT INTO THELOAI VALUES('BALLAD');
INSERT INTO THELOAI VALUES('BOLERO');
INSERT INTO THELOAI VALUES('VIETNAMESE MUSIC');
INSERT INTO THELOAI VALUES('INDIE');
INSERT INTO THELOAI VALUES('R&B');
INSERT INTO THELOAI VALUES('CHILL');
INSERT INTO THELOAI VALUES('LOVE');

INSERT INTO ALBUM VALUES (N'SUY',N'W/N',N'RAP','2019-07-31','SZA2.jpg'); 
INSERT INTO ALBUM VALUES (N'DREAMEE',N'AMEE',N'LOVE','2020-06-28','SZA1.jpg'); 
INSERT INTO ALBUM VALUES (N'LINH KA RADIO',N'LINH KA',N'LOVE','2023-12-11','THE WEEKND.jpg'); 
INSERT INTO ALBUM VALUES (N'KYS',N'SZA',N'CHILL','2017-08-01','BILLIE EILISH.jpg');

INSERT INTO NHAC VALUES (N'BAD GUY',N'INDIE','SUY',N'BILLIE EILISH','4:34','White shirt now red, my bloody nose
Sleepin'', you''re on your tippy toes
Creepin'' around like no one knows
Think you''re so criminal
Bruises on both my knees for you
Don''t say thank you or please
I do what I want when I''m wanting to
My soul, so cynical
So you''re a tough guy
Like it really rough guy
Just can''t get enough guy
Chest always so puffed guy
I''m that bad type
Make your mama sad type
Make your girlfriend mad tight
Might seduce your dad type
I''m the bad guy
Duh
I''m the bad guy
I like it when you take control
Even if you know that you don''t
Own me, I''ll let you play the role
I''ll be your animal
My mommy likes to sing along with me
But she won''t sing this song
If she reads all the lyrics
She''ll pity the men I know
So you''re a tough guy
Like it really rough guy
Just can''t get enough guy
Chest always so puffed guy
I''m that bad type
Make your mama sad type
Make your girlfriend mad tight
Might seduce your dad type
I''m the bad guy
Duh
I''m the bad guy
Duh
I''m only good at bein'' bad, bad
I like when you get mad
I guess I''m pretty glad that you''re alone
You said she''s scared of me?
I mean, I don''t see what she sees
But maybe it''s ''cause I''m wearing your cologne
I''m a bad guy
I''m-I''m a bad guy
Bad guy, bad guy
I''m a bad-','BILLIE EILISH.jpg','');
INSERT INTO NHAC VALUES (N'DIE FOR YOU REMIX',N'LOVE',N'HOT HITS VIETNAM',N'THE WEEKND','2:48','I''m findin'' ways to articulate
The feeling I''m goin'' through
I just can''t say I don''t love you
''Cause I love you, yeah
It''s hard for me to communicate the thoughts that I hold
But tonight I''m gon'' let you know
Let me tell the truth
Baby let me tell the truth, yeah
You know what I''m thinkin''
See it in your eyes
You hate that you want me
Hate it when you cry
You''re scared to be lonely
''Specially in the night
I''m scared that I''ll miss you
Happens every time
I don''t want this feelin''
I can''t afford love
I try to find reason to pull us apart
It ain''t workin'' ''cause you''re perfect
And I know that you''re worth it
I can''t walk away, oh!
Even though we''re going through it
If it makes you feel alone
Just know that I would die for you
Baby I would die for you, yeah
The distance and the time between us
It''ll never change my mind, ''cause baby
I would die for you
Baby I would die for you, yeah
I''m finding ways to manipulate the feelin'' you''re goin'' through
But baby girl, I''m not blamin'' you
Just don''t blame me too, yeah
''Cause I can''t take this pain forever
And you won''t find no one that''s better
''Cause I''m right for you, babe
I think I''m right for you, babe
You know what I''m thinkin''
See it in your eyes
You hate that you want me
Hate it when you cry
It ain''t workin'' ''cause you''re perfect
And I know that you''re worth it
I can''t walk away, oh!
Even though we''re going through it
If it makes you feel alone
Just know that I would die for you
Baby I would die for you, yeah
The distance and the time between us
It''ll never change my mind, ''cause baby
I would die for you
Baby I would die for you, yeah
I would die for you
I would lie for you
Keep it real with you
I would kill for you, my baby
I''m just sayin'', yeah
I would die for you
I would lie for you
Keep it real with you
I would kill for you, my baby
Na-na-na, na-na-na, na-na-na
Even though we''re going through it
If it makes you feel alone
Just know that I would die for you
Baby I would die for you, yeah
The distance and the time between us
It''ll never change my mind, ''cause baby
I would die for you
Baby I would die for you, yeah babe
Die for you
','THE WEEKND.jpg','');
INSERT INTO NHAC VALUES (N'DITTO',N'LOVE',N'LINH KA RADIO',N'NEWJEANS','3:09','Hoo-ooh-ooh-ooh
Hoo-ooh-ooh-ooh
Stay in the middle
Like you a little
Don''t want no riddle
말해줘 say it back, oh, say it ditto
아침은 너무 멀어 so say it ditto
훌쩍 커버렸어
함께한 기억처럼
널 보는 내 마음은
어느새 여름 지나 가을
기다렸지 all this time
Do you want somebody
Like I want somebody?
날 보고 웃었지만
Do you think about me now? Yeah
All the time, yeah, all the time
I got no time to lose
내 길었던 하루, 난 보고 싶어
Ra-ta-ta-ta 울린 심장 (ra-ta-ta-ta)
I got nothing to lose
널 좋아한다고 ooh-whoa, ooh-whoa, ooh-whoa
Ra-ta-ta-ta 울린 심장 (ra-ta-ta-ta)
But I don''t want to
Stay in the middle
Like you a little
Don''t want no riddle
말해줘 say it back, oh, say it ditto
아침은 너무 멀어 so say it ditto
I don''t want to walk in this 미로
다 아는 건 아니어도
바라던 대로 말해줘 say it back
Oh, say it ditto
I want you so, want you, so say it ditto
Not just anybody
너를 상상했지
항상 닿아있던
처음 느낌 그대로 난
기다렸지 all this time
I got nothing to lose
널 좋아한다고 ooh-whoa, ooh-whoa, ooh-whoa
Ra-ta-ta-ta 울린 심장 (ra-ta-ta-ta)
But I don''t want to
Stay in the middle
Like you a little
Don''t want no riddle
말해줘 say it back, oh, say it ditto
아침은 너무 멀어 so say it ditto
I don''t want to walk in this 미로
다 아는 건 아니어도
바라던 대로 말해줘 say it back
Oh, say it ditto
I want you so, want you, so say it ditto
Hoo-ooh-ooh-ooh
Hoo-ooh-ooh-ooh','NEWJEANS.jpg','');
INSERT INTO NHAC VALUES (N'LOVE LANGUAGE',N'CHILL',N'M-TP',N'SZA','3:05','Said, "Patience ain''t no virtue with you"
I done wasted plenty time pacin'' around, hate this coupe
You had bitches on the side and let my mind wonder too
You relentless, nigga, I don''t need rent, nigga
Text me like I''m waitin'' for you to come lie to me
Ruin my day, sayin'' shit to hurt me, I can''t compete
Still on the way, I lay awake if you''re not around me
I''m so on to you, still gone for you
Needin'' you to talk to me in your love language
Show me, yeah, how to connect to you
Help me understand
How you speak your love language
Bad as I wanna be yours, I can''t get with your program
Sexin'' like a slow jam, stick around ''cause I want to
Bad as I wanna keep focus, you remind me I''m imperfect
And it sucks to admit
Nobody put that purpose in me like you do, still
Nobody get that work up out me like you do
Nobody get the truth about me quite like you
You the definition of my right hand
Never mind ridin'' backseat when you lead me
Talk to me in your love language
Show me, yeah, how to connect to you
Help me understand
How you speak your love language
Call me like you can''t suppress memories of me
Call me like you got confessions queued up
Like your last bitch, lookin'' chewed up, baby
Call me like you don''t regret missin'' this old thing back
Call me
You know the difference between me and chickens
You don''t wanna be, be without me
You don''t wanna live
I don''t wanna go, ooh, no
I don''t wanna be alone, oh
All that I know is mirrors inside me
They recognize you, please don''t deny me','SZA2.jpg','');
INSERT INTO NHAC VALUES (N'SHIRT',N'LOVE',N'DREAMEE',N'SZA','4:53','Kiss me, dangerous
Been so lost without you all around me
Get anxious
Lead me, don''t look back, it''s all about you
In the dark right now
Feeling lost, but I like it
Comfort in my sins, and all about me
All I got right now
Feel the taste of resentment
Simmer in my skin, it''s all about
Blood stain on my shirt, new bitch on my nerves
Old nigga got curved, going back on my word
Damn, bitch, you so thirsty
Still don''t know my worth, still stressing perfection
Let you all in my mental, got me looking too desperate
Damn, you ain''t deserve
Broad day, sunshine, I''ll find a way to fuck it up still
Can''t cry about the shit that I can''t change
Just my mind, gotta get outta here
Tough crowd, I hate it, can''t stay
In the dark right now
Feeling lost, but I like it
Comfort in my sins, and all about me
All I got right now
Feel the taste of resentment
Simmer in my skin, it''s all about
Blood stain on my shirt, new bitch on my nerves
Old nigga got curved, going back on my word
Damn, bitch, you so thirsty
Still don''t know my worth, still stressing perfection
Let you all in my mental, got me looking too desperate
Damn
It''s what you say and how you do me
How I''m ''posed to trust, baby? ''Posed to love?
It ain''t ''posed to hurt this way, all I need is the best of you
How I got to say it? Give me all of you
In the dark right now
Feeling lost, but I like it
Comfort in my sins, and all about me
All I got right now
Feel the taste of resentment
Simmer in my skin, it''s all about
Blood stain on my shirt, new bitch on my nerves
Old nigga got curved, going back on my word
Damn, bitch, you so thirsty
Still don''t know my worth, still stressing perfection
Let you all in my mental, got me looking too desperate
Damn, you ain''t deserve
','SZA1.jpg','');
INSERT INTO NHAC VALUES (N'SNOOZE',N'LOVE',N'DREAMEE',N'SZA','4:53','I''ll touch that fire for you
I do that three, four times again, I testify for you
I told that lie, I''d kill that bitch
I do what all of them around you scared to do, I''m not
Long as you juggin'' out here for me, I got it
Mobbin'', schemin'', lootin'', hide your bodies
Long as you dreamin'' ''bout me, ain''t no problem
I don''t got nobody, just with you right now
Tell the truth, I look better under you
I can''t lose when I''m with you
How can I snooze and miss the moment?
You just too important
Nobody do body like you do
I can''t lose when I''m with you
I can''t just snooze and miss the moment
You just too important
Nobody do body like you do, you do
In the droptop ride with you, I feel like Scarface (Scarface)
Like that white bitch with the bob, I''ll be your main one (main one)
Let''s take this argument back up to my place
Sex remind you, I''m nonviolent, I''m your day one
We ain''t had shit, yeah, it was magic, yeah
Smash and grab shit, yeah
Nasty habits take a hold when you not here
Ain''t a home when you not here
Hard to grow when you not here, I''m sayin''
I can''t lose when I''m with you
How can I snooze and miss the moment?
You just too important
Nobody do body like you do
I can''t lose when I''m with you
How can I snooze and miss the moment?
You just too important
Nobody do body like you do, you do
Main one ridin''
How you frontin'' on me and I''m the main one tryin''?
How you blame it on me and you the main one lyin''?
How you threatenin'' to leave and I''m the main one cryin''?
Just tryna be your everything
Main one ridin''
How you frontin'' on me and I''m the main one tryin''?
How you blame it on me and you the main one lyin''?
How you threatenin'' to leave and I''m the main one cryin''?
I can''t lose when I''m with you, ooh
How can I snooze and miss the moment?
You just too important
Nobody do body like you do
I can''t lose when I''m with you
How can I snooze and miss the moment?
You just too important
Nobody do body like you do, you do
Nah, nah, nah, nah
I think I know, whoa-oh
See, no, I can''t lose
I think I know, ooh-whoa, ooh-whoa-oh','SZA2.jpg','');
insert into NHAC values('DO4LOVE','LOVE','SUY','SNOH AELEGRA','3:09',
'I guess you wonder where I''ve been
I searched to find a love within
I came back to let you know
Got a thing for you and I can''t let go
My friends wonder what is wrong with me
Well, I''m in a daze from your love, you see
I came back to let you know
Got a thing for you and I can''t let go
Some people go around the world for love
But they may never find what they dream of
What you won''t do, do for love (do for love)
You''ve tried everything, but you won''t give up
And in my world, only you
Make me do for love, what I would not do
Now my friends wonder what is wrong with me
Well, I''m in a daze from your love, you see
Now I came back to let you know
Got a thing for you and I can''t let go
What you won''t do, do for love (do for love)
You''ve tried everything, but you won''t give up
And in my world, only you (only you)
Make me do for love, what I would not do (would not do)','SNOH.jpg','')
INSERT INTO NHAC VALUES (N'WASTE MY TIME',N'LOVE',N'DREAMEE',N'ARI LENNOX','3:22','Ooh, ooh, ooh
Ooh, ooh, ooh
No dick makin'' me stupid
No funny Valentine lovin'' from Cupid
Need someone to get to it
I see you beggin'' so there ain''t no choosin''
There must be a glitch in my phone
I''m ready for the switch to turn me on
Won''t you come and do something fun to me
''Cause I''m tryna have you
Waste my time, get on my line
''Cause I got the time to waste (ayy)
Use that mouth (ayy), blow this back out (ayy)
Back up every word you say (say sock it to me)
Waste my time, get on my line
Then you can be on your way
Use that mouth, pull a track out (ooh-ooh)
Back up every word you say
Every word (every word)
Every word, every, back up every word
Every word, yeah, yeah
Back up every word you say (ah, ah)
''Cause I''m wide open, sesame
See when you texting me, keep the same energy (oh oh)
Baby, there ain''t no rules (ain''t no rules)
Way too grown for the bullshit
That triple X on me, we ain''t school kids
So come over, and do what it do
''Cause I''m tryna have you
Waste my time, get on my line
''Cause I got the time to waste (ayy)
Use that mouth (ayy), blow this back out (ayy)
Back up every word you say (ooh, sock it to me)
Waste my time, get on my line (sock it to me)
Then you can be on your way
Use that mouth, pull a track out (ooh ooh)
Back up every word you say
Said every word (every word)
Every word, every, back up every word
Every word, yeah, yeah, (oh-ow)
Back up every word you say
Said every word (every word)
Said every word, baby, back up every word
Every word, yeah, yeah (ah ah)
Back up every word you say (ayy-ayy, ayy)','ARI LENNOX.jpg','')
INSERT INTO NHAC VALUES (N'WHAT IS IT',N'LOVE',N'DREAMEE',N'DOECHII','3:09','What it is, ho? What''s up?
Every good girl needs a little thug
Every block boy needs a little love
If you put it down, I''ma pick it up, up, up
Can''t you just see, it''s just me and you?
Panoramic view, that''s my point of view, bae
All about me, that''s the energy
That''s that lemon pepper thing, I''m a ten-piece, baby
Bedroom bully in the bando
He gon'' make it flip, do it with no handles
Never switchin'' sides, only switchin'' angles
Ooh, we go crazy like Rambo
What it is, ho? (What it is?) What''s up? (What''s up?)
Every good girl needs a little thug (thug)
Every block boy needs a little love (love)
If you put it down, I''ma pick it up, up (ayy), up (ayy)
What it is, ho? (What it is?) What''s up? (What''s up?)
Every good girl needs a little thug (thug)
Every block boy needs a little love (love)
If you put it down, I''ma pick it up, up (ayy), up (ayy)
Hit ''em up, hit ''em up
In the truck, got it tucked, he get it up, like it''s stuck
All night, I like, you ain''t gotta say it when you know it''s on site
She got e''rythin'' he wanted, a nice body, ass fat
Behind every gangsta, a shawty solid that got his back
He know who to come to e''ry time the world handlin'' him bad
The way he call first, but still he always put her last (always put her last, yeah)
I''m pourin'' out this glass, my body fightin'' off that gas (off that gas, yeah)
On smoke box, I kill ''em, that zaza pack kickin'' my ass (kickin'' my ass, yeah)
In the studio dosin'', all I can keep from fallin'' asleep
I hate that for you niggas ain''t got no bread, but tryna beef
Bein'' black in America, is the hardest thang to be (baby, tell me, yeah)
E''ry thug need a lil'' love too, baby, how ''bout me?
Told her, "Don''t call me a stink ''cause I smell like money"
I puttin'' down the greatest babe, hold this here for me
I took her from a nigga, we vibin'', two weeks outta the country
So she had a lil'' situation, but I could tell it ain''t ''bout nothin''
Now me and her rappin'', bitch, she say, "Don''t hush me", I say, "Don''t rush me"
And I can tell how much she like a nigga by the way she suck it, ho, what it is?
What it is, ho? (What it is?) What''s up? (What''s up?)
Every good girl needs a little thug (thug)
Every block boy needs a little love (love)
If you put it down, I''ma pick it up, up (ayy), up (ayy)
What it is, ho? (What it is?) What''s up? (What''s up?)
Every good girl needs a little thug (thug)
Every block boy needs a little love (love)
If you put it down, I''ma pick it up, up (ayy), up (ayy)
I don''t care if you run the streets
Long as you comin'' home to me
I love the way you walk and the way you speak
He gon'' keep it real, that''s the deal
That''s the reason that I speed down, down (down, down, down, down)
I put that all on my name (all on my name)
Yeah, that''s an even exchange (tell me what it is)
Stay on yo'' deal, we gon'' tell ''em, we gon'' tell ''em what, babe
What it is, ho? (What it is?) What''s up?
Every good girl needs a little thug (needs a little thug)
Every block boy needs a little love (a little love, yeah)
If you put it down, I''ma pick it up, up, up
What it is, ho? What''s up?
Every good girl needs a little thug (needs a little love)
Every block boy needs a little love (needs a little love, thug)
If you put it down, I''ma pick it up, up (ayy), up (ayy)
Back it up and do, and do it like that, yeah (what it is? What''s up?)
Back it up and do it like that, yeah (ho)
Back it up, back it and do it like that, yeah
Back it up gon'' and do it like that, yeah (woo)
Hey, what it is? Ayy, what''s up?
J. White Did It (yup, yup), more is on the way (yup, yup, yeah)
We need a lil'' love, you know I mean? (Yeah, ayy-ayy, woo), ride out, whoa','DOECHII.jpg','')
INSERT INTO NHAC VALUES (N'WHAT YOU WANNA TRY',N'LOVE',N'DREAMEE',N'MASEGO','2:41','Where we going?
Detroit
Trying get that Mulah
I''m in Detroit with Khadijah
Fighting feelings, rolling weed, yeah
It''s organic how we speed through
Midnight moments, foreign vehicles
Fifteen minutes of fame or
Fifteen minutes away from
Finding love in a waveform
Is you someone to wait for?
Is you tryna stay on the ride
Or is you tryna, is you tryna leave the vibe?
Take you to the hotel if you wanna slide
Renaissance drive, baby, make a right
Is you tryna fall for the night
Or is you tryna, is tryna fall for life?
City full of people, you got options right?
Baskin robins, baby, what you wanna try?
What''s your flavor?
Tell me, what''s your flavor?
What''s your flavor?
Tell me, what''s your flavor?
Issa Wang Dang Thang
West side 7-mile slang
Coney Island Hangs
Tell me, what''s your flavor?
I think she want me for my loot
I think she wanna get some food
So thick that body make a man commute
I think she want her
Fifteen minutes of fame or
Fifteen minutes away from
Finding love in a waveform
Is you someone to wait for
Is you tryna stay on the ride
Or is you tryna, is you tryna leave the vibe?
Take you to the hotel if you wanna slide
Renaissance drive, baby, make a right
Is you tryna fall for the night
Or is you tryna, is tryna fall for life?
City full of people, you got options right?
Baskin robins, baby, what you wanna try?
What''s your flavor?
Tell me, what''s your flavor?
What''s your flavor?
Tell me, what''s your flavor?
Issa Wang Dang Thang
West side 7-mile slang
Coney Island Hangs
Tell me, what''s your flavor?
','MASEGO.jpg','')
INSERT INTO NHAC VALUES (N'WHERE I GO',N'LOVE',N'DREAMEE',N'NXWORRIES','4:16','Mmh, mh-mh, mmh, mh
She wanna know where I''ve been
And who I seen
She wanna know where I''m goin''
And can we meet (tryna get it on)
She say I''m so terrible
But this is me (that''s not my fault)
I love her from head to toe (head to toe)
And in between (girl, I love all of you)
I know we do a lot of back and forth
I know we do a lot of fast and slow
I know I''m gon'' contradict myself
I know you gon'' ride the dick like a pro
I know you got a lot of faith in me
I know you put a lot of things on hold
I hope you got a policy
You know you got a lock on the whole thing
I had to tell you somethin'', tell you somethin'' before I go
I left a lil'' somethin'' special in the envelope
I had to mail you somethin'', tell me when it''s at the door
And when you open it, I hope you get hysterical
I had to share it with somebody that I really trust
So when we celebrate, I''ma hit you up
I had to go and find a runnin'' mate who didn''t rush
I hope you know it was imperative for both of us
She wanna know where I''ve been (where I''ve been)
And who I seen (baby, there''s no one)
She wanna know where I''m goin''
And can we meet (tryna get it on)
She say I''m so terrible (terrible)
But this is me (that''s not my fault)
I love her from head to toe (head to toe)
And in between (girl, I love all of you)
I know we do a lot of back and forth
I know we do a lot of fast and slow
I know I''m gon'' contradict myself
I know you gon'' ride the dick like a pro (I know it)
I know you got a lot of faith in me
I know you put a lot of things on hold
I hope you got a policy
You know you got a lock on the whole thing
You know that you my motherfucking cinnamon apple (yeah)
Know that when it comes to loving you I''m a natural
Know how much it hurt me, that''s in the past though
When I''m all alone, better not be with that ho, oh
I had to deal with your sides and your pieces (pieces)
I put two and two together in the sequence
I promise I am trying not to be on defense
But I''m reminded of it every time you leave
She wanna know where I''ve been
And who I seen (baby, baby, oh)
She wanna know where I''m goin'' (you goin'')
And can we meet (tryna get it on, ah)
She say I''m so terrible (terrible)
But this is me (that''s not my fault)
(You keep calling me crazy but that''s how you made me)
I love her from head to toe (you should know that)
And in between (girl, I love all of you)
Lot of back and forth (yeah)
Lot of fast and slow
Contradict myself (contradict myself)
Gon'' suck the dick like a pro (I know it)
Lot of faith in me
Lot of things on hold
I know you got it, got it, ooh, baby
Ooh
I love you
','NXWORRIES.jpg','')
INSERT INTO NHAC VALUES (N'WHITE RICE DEJA VUU',N'LOVE',N'DREAMEE',N'MUSIQ SOULCHILD','3:50','Uh, ten, twenty, thirty hours
Head, shower, bed (ayy)
I could lay here with ya ''til the day switch, ooh (ooh)
Love like a shot of Henny, I would chase it
Ooh, ooh, ooh-ooh-ooh (ooh)
You would think it''s white rice by the way we spoon (white rice)
Shut the blinds and we sleep ''til noon (night, night)
Better hold tight, might be leaving soon
But can''t leave behind the smell of your perfume
Girl, it feels like déjà vu
Will I have your love when I need it, need it? (Déjà vu)
Déjà vu
From the morning light to the evening, evening
You wouldn''t mind it if I stayed all night
Girl, you make it hard to say goodbye
Like déjà vu
Familiar feeling
Don''t say you''re leaving, no
In these four walls, I''ll take you all kind of places
You walk around this house in your drawers
But I think you''re better off naked, uh, ayy
She wanna ride like an ''84 Caprice (yeah, yeah)
You''ll be my passenger all between the sheets (all between the sheets, all between the sheets)
You would think it''s white rice by the way we spoon (white rice)
Shut the blinds and we sleep ''til noon (night, night)
Better hold tight might be leaving soon
Now, it''s just way too quiet when you''re not in this room
It feels like déjà vu
Will I have your love when I need it, need it? (Déjà vu)
Déjà vu
From the morning light to the evening, evening
You wouldn''t mind it if I stayed all night
Girl, you make it hard to say goodbye
Like déjà vu
Familiar feeling
Don''t say you''re leaving, no
Don''t wake up, let''s just keep dreaming
Won''t need words to speak your love language
For what it''s worth, your time ain''t wasted here, oh no (oh no)
(Looks like you found you some peace) don''t wake up
(Let''s just keep dreaming) (shawty I''m sure, seen it all before, hey)
(Won''t need words to speak your love language)
(Looks like you found you some peace)
(Shawty I''m sure, seen it all before)
Feels like
Déjà vu
Will I have your love when I need it, need it?
Déjà vu
From the morning light to the evening, evening
You wouldn''t mind it if I stayed all night
Girl, you make it hard to say goodbye
Like déjà vu
Familiar feeling
Don''t say you''re leaving, no
Looks like you found you some peace
Shawty I''m sure, seen it all before, ayy
Looks like you found you some peace
Shawty I''m sure, seen it all before
Looks like you found you some peace
Shawty I''m sure, seen it all before, ayy
Looks like you found you some peace
Shawty I''m sure, seen it all before','MUSIQ SOULCHILD.jpg','')
INSERT INTO NHAC VALUES (N'YUMMY',N'LOVE',N'DREAMEE',N'JUSTIN BIEBER','3:50','Yeah, you got that yummy-yum
That yummy-yum, that yummy-yummy
Yeah, you got that yummy-yum
That yummy-yum, that yummy-yummy
Say the word, on my way
Yeah, babe, yeah, babe, yeah, babe
Any night, any day
Say the word, on my way
Yeah, babe, yeah, babe, yeah, babe
In the mornin'' or the late
Say the word, on my way
Bona fide stallion
Ain''t in no stable, no, you stay on the run
Ain''t on the side, you''re number one
Yeah, every time I come around, you get it done
Fifty-fifty, love the way you split it
Hundred racks, help me spend it, babe
Light a match, get litty, babe
That jet set, watch the sunset kinda, yeah, yeah
Rollin'' eyes back in my head, make my toes curl, yeah, yeah
Yeah, you got that yummy-yum
That yummy-yum, that yummy-yummy
Yeah, you got that yummy-yum
That yummy-yum, that yummy-yummy
Say the word, on my way
Yeah, babe, yeah, babe, yeah, babe
Any night, any day
Say the word, on my way
Yeah babe, yeah babe, yeah babe
In the mornin'' or the late
Say the word, on my way
Standin'' up, keep me on the rise
Lost control of myself, I''m compromised
You''re incriminating, no disguise
And you ain''t never runnin'' low on supplies
Fifty-fifty, love the way you split it
Hundred racks, help me spend it, babe
Light a match, get litty, babe
That jet set, watch the sunset kinda, yeah, yeah
Rollin'' eyes back in my head, make my toes curl, yeah, yeah
Yeah, you got that yummy-yum
That yummy-yum, that yummy-yummy (You stay flexin'' on me)
Yeah, you got that yummy-yum (Yeah, yeah)
That yummy-yum, that yummy-yummy
Say the word, on my way
Yeah, babe, yeah, babe, yeah, babe (Yeah, babe)
Any night, any day
Say the word, on my way
Yeah babe, yeah babe, yeah babe (Yeah, babe)
In the mornin'' or the late
Say the word, on my way
Hop in the Lambo'', I''m on my way
Drew House slippers on with a smile on my face
I''m elated that you are my lady
You got the yum, yum, yum, yum
You got the yum, yum-yum, woah
Woah-ooh
Yeah, you got that yummy-yum
That yummy-yum, that yummy-yummy
Yeah, you got that yummy-yum
That yummy-yum, that yummy-yummy
Say the word, on my way
Yeah, babe, yeah, babe, yeah, babe (Yeah, babe)
Any night, any day
Say the word, on my way
Yeah, babe, yeah, babe, yeah, babe (Yeah, babe)
In the mornin'' or the late
Say the word, on my way
','JUSTIN BIEBER.jpg','')
INSERT INTO NHAC VALUES (N'WAITING FOR YOU',N'LOVE',N'DREAMEE',N'MONO','4:28',N'Chiều đang dần buông hạt mưa rơi xuống không gian lắng yên
Suy tư vấn vương ngồi mộng mơ
Đơn phương nhớ đến một nàng thơ
Gió đông ùa về mang những ê chề (woh)
Ngỡ là trái tim khô cằn héo úa sẽ thôi buồn đau
Nhưng thật cay đắng khi biết là (ú oà)
Mình chỉ là một người đến sau (hey)
Biết em đã có người ở gần bên
Nhưng anh sẽ vẫn đứng ngay đây và chờ em
Mưa giông bão tố chẳng quan tâm đến ngày đêm
Kẻ si tình này chọn ở phía sau thầm nhớ mong em bae bae
Vì say mê ánh mắt yêu luôn cả bờ môi
Muốn nói với cả thế giới chỉ thương em mà thôi
Đắm đuối uh cháy lên ngọn lửa tình yêu
Bùng lên mạnh mẽ và thiêu đốt baby that’s what I feel
My girl I’m waiting for you
Một bông hồng xinh tươi thắm huh trông em kiêu sa
Đôi chân thướt tha mặn mà (uh)
Hương thơm miên man dịu dàng (uh)
Dáng duyên nụ cười say đắm yêu người
Ngỡ là trái tim khô cằn héo úa sẽ thôi buồn đau
Nhưng thật cay đắng khi biết là (ú oà)
Mình chỉ là một người đến sau (hey)
Biết em đã có người ở gần bên
Nhưng anh sẽ vẫn đứng ngay đây và chờ em
Mưa giông bão tố chẳng quan tâm đến ngày đêm
Kẻ si tình này chọn ở phía sau thầm nhớ mong em bae bae
Vì say mê ánh mắt yêu luôn cả bờ môi
Muốn nói với cả thế giới chỉ thương em mà thôi
Đắm đuối uh cháy lên ngọn lửa tình yêu
Bùng lên mạnh mẽ và thiêu đốt baby that’s what I feel
My girl I’m waiting for you
I’m waiting for you (oh oh)
I’m waiting for you (oh oh)
Chờ em về đây với anh
Mình cùng đan bàn tay
Ấm áp bao đêm ngày
Yeah
Chờ em chờ em ừ thì chờ em
Chờ em chờ em chờ đến bao giờ
Biển khô cạn trời không còn đầy sao
Thì anh vẫn nơi đây và chờ em
Biết em đã có người ở gần bên
Nhưng anh sẽ vẫn đứng ngay đây và chờ em
Mưa giông bão tố chẳng quan tâm đến ngày đêm
Kẻ si tình này chọn ở phía sau thầm nhớ mong em bae bae
Vì say mê ánh mắt yêu luôn cả bờ môi
Muốn nói với cả thế giới chỉ thương em mà thôi
Đắm đuối uh cháy lên ngọn lửa tình yêu
Bùng lên mạnh mẽ và thiêu đốt baby that’s what I feel
My girl I’m waiting for you','MONO.jpg','')
INSERT INTO NHAC VALUES (N'VALENTINA',N'LOVE',N'DREAMEE',N'DANIEL CAESER','2:33','Valentina, baby
I only need one moment of time
To make you feel a way
From the first time I looked in your eyes
I knew that I would find
A way
To make
You mine
I know it''s late, and I know you got a man
Please understand, baby, he won''t (baby, he won''t)
Love you like I do (like I do)
Give me a break, I know that you want me too
Your loyal is cool, I understand
And I respect it
Valentina, baby
I only need one moment of time (moment of time)
To make you feel
A way
From the first time I looked in your eyes
I knew that I would find
A way
To make
You mine','DANIEL CAESER.jpg','')
INSERT INTO NHAC VALUES (N'USED TO ME',N'LOVE',N'DREAMEE',N'MARIO','2:52','How does pleasure turn to pain (pain)
Real love into shame (shame)
How I call out your name (name)
Aw it ain''t been the same baby, nah
Girl, I need you back with me active, yeah
I want your back with arch in it
''Cause these hoes tryna be choosy
But I know you thinking bout me
The way I''m thinking bout you and
What it''s gonna be when I pull up on you right now
If I don''t know you by now
We gon'' fuck or you gon'' kick me out babe (oh yeah)
You say I''m toxic lately
But you the one that be riding and driving me crazy
Babe where is the lie?
Girl don''t get used to your bed
If I''m not there with you
Girl don''t get used to me begging
But I''m fucking you tonight
''Cause we ain''t really got a lot to say
Girl I know what to do to drive you crazy
So baby come and get a hit of your thang
You can take advantage of me (Dolla Sign)
Uh
Take advantage of me baby, I can manage it
I''m only 10% in, can you handle it
We got Rio on the playlist and the candle lit (oh yeah)
I''m fucking you tonight (oh-oh-oh)
Back and forth, forth and back, switch positions
Like the middle, you the center of attention
Say my name, say my name, Dolla Sign (Dolla Sign)
Whose pussy is it baby, tell me that it''s mine (oh-oh-oh)
And you know I came to fuck it up (fuck it up)
Can''t get used to this shit, I''m always somewhere else
In my city, in your city, girl I''m gone, pull up (oh-oh) every time (baby)
You know I gon'' show you love
I just want to be the one (oh-oh yeah)
Don''t give that pussy to no one oh-oh-oh
Girl don''t get used to your bed (your bed)
If I''m not there with you (you)
Girl don''t get used to me begging (me begging)
When I''m fucking you tonight oh-oh-oh
We ain''t really got a lot to say
I know what to do to drive you crazy
Baby come and get a hit of your thang (oh yeah)
You can take advantage on me (-vantage on me yeah)
That''s my shit (that''s my shit)
Just like this (oh-oh-oh)
That''s my shit you lit (got you lit) oh yeah
You so thick (oh-oh-oh), that''s your ass, no waist
And your face, can''t waste no time
That shit gonna feel like, like, like','MARIO.jpg','')
INSERT INTO NHAC VALUES (N'THIS IS',N'LOVE',N'DREAMEE',N'ELLA MAI','3:26','Just wanna love you for my whole life
Do it for you, I''ma make time (make time)
I wanna kick it until midnight
Just to be with you ''til the sunrise (sunri-i-ise)
I think you''re making me crazy
Put my heart upon the main line, I (I)
Got this trust for you (you)
Got it ''cause I know what you like (I love ya)
Good and bad, thick and thin, flaws and all
And if you ever in the wrong, I''ll let you know
Yeah, I know that love is unpredictable
But I''m pretty damn sure that
This is the kinda love that keeps me up all night
This is what I wanna do for the rest of my life
This is something that can get every bit of my time
This is what they call real love, real love, real love
You saw my halo (halo)
Even on days when you should hate most (hate most)
Laid up, I''m stuck with you (like glue)
So tell me ''bout it, how''d ya day go? (I want you)
Kissing on me, missing on me
Every time I see you, got them feelings on me (so I''m)
Kissing on you and you''re loving on me
Yeah, I won''t play with your heart (I love ya)
Good and bad, thick and thin, flaws and all
And if you ever in the wrong, I''ll let you know
Yeah, I know that love is unpredictable
But I''m pretty damn sure that
This is the kinda love that keeps me up all night
This is what I wanna do for the rest of my life
This is something that can get every bit of my time
This is what they call real love, real love, real love
Oh, I know that you told me as long as you hold me, I couldn''t be safer
Oh, love me for the real me, don''t know what your deal is, but I''m with you ''til
Daylight, daylight, daylight, ''til the sun comes up
Dancing in the moonlight, moonlight, moonlight, this forever, this forever, this
This is the kinda love that keeps me up all night (kinda love that keeps me up all night)
This is what I wanna do for the rest of my life (for the rest of my life)
This is something that can get every bit of my time (every bit of my time)
This is what they call real love, real love, real love','ELLA MAI.jpg','')
INSERT INTO NHAC VALUES (N'TEMPORARY',N'LOVE',N'DREAMEE',N'6LACK','2:58','Don''t take it personal
You know I know it hurts to go
Carry the weight, and I ignore the pain
This might cost a lot, charge the game
Won''t miss the fightin'', but I know you''ll miss me
I just hope you get a feel to kiss me
We break, then shake, no fake, you made me smile
Sun, rain, or snow, girl, I want you around
That''s how I know I can''t give you up (oh)
Most times, it''s temporary
Most girls are temporary
Most feels are temporary
This time it''s not
This time, I can''t give you
That''s how I know I can''t give you up (oh)
Most times, it''s temporary
Most girls are temporary
Most feels are temporary
This time it''s not
This time, I can''t give you (yeah)
Got karma on my mind, so don''t talk bad on us
Bad features, I can''t tolerate them barkin'' on us
We part ways and keep operatin'', heartbreak for us
Got Patrón for us, yeah, breathe in, then breathe out
New links and new rules for myself, reach in to reach out
Soon as I hop out the whip
We break, then shake, no fake, you made me smile
Sun, rain, or snow, girl, I want you around
That''s how I know I can''t give you up (oh)
Most times, it''s temporary
Most girls are temporary
Most feels are temporary
This time it''s not
This time, I can''t give you
That''s how I know I can''t give you up (oh)
Most times, it''s temporary
Most girls are temporary
Most feels are temporary
This time it''s not
This time, I can''t give you (yeah, yeah)','6LACK.jpg','')
INSERT INTO NHAC VALUES (N'SURE THING',N'LOVE',N'DREAMEE',N'MIGUEL','3:21','Love you like a brother
Treat you like a friend
Respect you like a lover
Oh, oh, oh, oh, oh, oh
You could bet that, never gotta sweat that (oh, oh, oh, oh, oh)
You could bet that, never gotta sweat that (yeah, yeah, yeah)
You could bet that, never gotta sweat that
You could bet that, never gotta sweat that (yeah)
If you be the cash
I''ll be the rubber band
You be the match
I will be a fuse, boom
Painter, baby, you could be the muse
I''m the reporter, baby, you could be the news
''Cause you''re the cigarette and I''m the smoker
We raise a bet ''cause you''re the joker
Checked off, you are the chalk
And I can be the blackboard
You can be the talk
And I can be the walk, yeah
Even when the sky comes falling
Even when the sun don''t shine
I got faith in you and I
So put your pretty little hand in mine
Even when we''re down to the wire, babe
Even when it''s do or die
We could do it, baby, simple and plain
''Cause this love is a sure thing
You could bet that, never gotta sweat that (yeah, yeah, yeah)
You could bet that, never gotta sweat that
You could bet that, never gotta sweat that
You could bet that, never gotta sweat that
You could be the lover, I''ll be the fighter, babe
If I''m the blunt (uh), you could be the lighter, babe
Fire it up
Writer, baby, you could be the quote, yeah (uh)
If I''m the lyric, baby, you could be the note (uh), record that
Saint I''m a sinner (uh), prize I''m a winner (uh)
And it''s you, what did I do to deserve that?
Paper, baby, I''ll be the pen
Say that I''m the one ''cause you are a ten
Real and not pretend
Even when the sky comes falling (yeah, yeah, yeah, yeah, yeah)
Even when the sun don''t shine (yeah)
I got faith in you and I
So put your pretty little hand in mine
Even when we''re (you could bet that, never gotta sweat that)
Down to the wire, baby
Even when it''s do or die (you could bet that, never gotta sweat that)
(You could bet that, never gotta sweat that)
We could do it baby, simple and plain
(You could bet that, never gotta sweat that)
''Cause this love is a sure thing
Uh, now rock with me, baby
Let me hold you in my arms
Talk with me babe, yeah, yeah
Uh, now rock with me baby
Let me hold you in my arms
Talk with me babe, yeah, yeah
This love between you and I is simple as pie, baby
Yeah, it''s such a sure thing (it''s such a sure thing)
Oh, it such a sure thing (it''s such a sure thing)
Even when (you could bet that, never gotta sweat that)
The sky comes falling
Even when (you could bet that, never gotta sweat that)
The sun don''t shine
(You could bet that, never gotta sweat that)
I got faith in you and I
So put your pretty little hand in mine (you could bet that, never gotta sweat that)
Even when (you could bet that, never gotta sweat that)
We''re down to the wire, babe
Even when (you could bet that, never gotta sweat that)
It''s do or die (you could bet that, never gotta sweat that)
We could do it, baby, simple and plain
(You could bet that, never gotta sweat that)
''Cause this love is a sure thing
Love you like a brother (you could bet that, never gotta sweat that)
Treat you like a friend (you could bet that, never gotta sweat that)
Respect you like a lover (you could bet that, never gotta sweat that)
Oh, oh, oh, oh, oh, oh
','MIGUEL.jpg','')
INSERT INTO NHAC VALUES (N'SO BE IT',N'LOVE',N'DREAMEE',N'ALEX VAUGHN','3:13','Say that I don''t miss you but I do
Say that I don''t care, that''s not the truth
Ignore my intuition once again
All for me to say you''re still my friend
Seems like everybody knows but me
Didn''t even know you moved up right the street
All the things you said we wouldn''t be (be, be, be)
So be it
It''s been a hard time adjusting
So be it
Instead of lettin'' me know, you kept secrets
But if I cut you this deep, you''d be in pieces
If that''s how you really feel, then so be it
So be it, so be it
If that''s who you are, then so be it
So be it
So be it
So be it
That night when we linked up on the east side
Rolled one, never would''ve thought that''d be the last time
Tried to reassure you, Lord knows I tried
Is the distance the reason that I''m the bad guy?
Don''t give a fuck how it''s affecting me
Tryin'' to keep up, I''m not your enemy
Makin'' decision off feelings, I can''t make you see the way I do
All is for nothing, all I can say is
So be it
It''s been a hard time adjusting
So be it
Instead of lettin'' me know, you kept secrets
But if I cut you this deep, you''d be in pieces
You''d be in pieces
Even if I did
You would be the last up on that list
I still can''t believe you did that shit
But in my heart I still want to forgive
Came to terms that we could never get
Back to what we used to be
Everything''s still new to me
Won''t be no love lost
So be it
It''s been a hard time adjusting
So be it
Instead of lettin'' me know, you kept secrets
But if I cut you this deep, you''d be in pieces
If that''s how you really feel, then so be it
So be it, so be it
If that''s who you are, then so be it
So be it
So be it
So be it','ALEX VAUGHN.jpg','')


INSERT INTO YEUTHICH VALUES ('USER',N'BAD GUY',N'SUY');
INSERT INTO YEUTHICH VALUES ('USER123',N'SO BE IT',N'DREAMEE');

SELECT * FROM ALBUM
SELECT * FROM NGHESI
SELECT * FROM NHAC
SELECT * FROM TAIKHOAN
SELECT * FROM THELOAI
SELECT * FROM YEUTHICH