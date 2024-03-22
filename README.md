# Expense Tracker Application

## ความเป็นมา
โปรแกรม Record income and expenses Api นี้ถูกสร้างขึ้นเพื่อช่วยในการบันทึกข้อมูลรายรับและรายจ่ายของผู้ใช้ โดยมีวัตถุประสงค์เพื่อจัดการการเงินและติดตามค่าใช้จ่ายอย่างมีประสิทธิภาพ

## แนวคิด
- เพิ่มรายการรายรับและรายจ่ายใหม่ได้
- แสดงรายการรายรับและรายจ่ายในหน้าหลัก
- แสดงจำนวนรายการทั้งหมด
- แสดงยอดเงินคงเหลือ
- คำนวณยอดรวมของรายรับและรายจ่าย
- สามารถแก้ไขรายการรายรับรายจ่ายแต่ละรายการได้
- สามารถลบรายการรายรับรายจ่ายแต่ละรายการได้
- สามารถค้นหาชื่อรายการได้

## วิธีการใช้

1.ภาพต่อไปนี้คือภาพของหน้าหลักของโปรแกรม

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/5394139b-f5ff-4270-a4db-1a2fca6e9143)

2.เพิ่มรายการรายรับหรือรายจ่ายโดยคลิกที่ปุ่ม + บนหน้าหลัก
   
   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/f2e336f1-4832-4071-be0c-ca415b0e03c1)

3.กรอกข้อมูลที่จำเป็น คือ ชื่อรายการ เงินจำนวน เลือกวันเดือนปี ประเภท

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/0255173f-b74b-4951-bb44-839c60f79209)

4.กดปุ่ม "Add" เพื่อบันทึกข้อมูล

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/6470c88b-df3c-45b3-b5e4-9dfab13c1ed4)

5.สามารถย้อนกลับไปหน้าหลักได้โดยกดปุ่มดังภาพ

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/e077c22e-3cca-4661-ba25-e1c170ace865)

6.เพื่อแก้ไขหรือลบรายการ ให้คลิกที่ปุ่ม "Edit" หรือ "Delete" ตามต้องการที่หน้าหลัก

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/cef7f557-3a63-4083-8999-3bd592d455c8)

7.หน้าต่าง Edit ข้อมูลรายการที่เลือก

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/bd7d0b4c-08a8-48b5-988a-85643da67864)

8.กดปุ่ม Update เพื่อทำการ Update ข้อมูลที่เลือก

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/0439b266-a040-4c49-9e4b-a3432d8dafd0)

9.หน้าต่าง Delete ข้อมูลรายการที่เลือก

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/ecd70a7f-8906-48b6-bbf8-0525381afb39)

10.กดปุ่ม Delete เพื่อทำการ Delete ข้อมูลที่เลือก

   ![image](https://github.com/Syxsaran/Record-in-ex-api/assets/96071669/883d7274-2f45-4c98-8e7b-13c204e46642)


## ผู้พัฒนา
โปรแกรมนี้ถูกพัฒนาโดยนักศึกษา ตามรายละเอียดด้านล่าง

- ชื่อ: นาย ศรันย์ ซุ่นเส้ง 
- รหัสนักศึกษา: 643450086-6

## ข้อกำหนดและเงื่อนไข
โปรแกรมนี้ใช้ Firebase Realtime Database เป็นพื้นที่เก็บข้อมูล โปรดตรวจสอบข้อกำหนดและเงื่อนไขการใช้บริการของ Firebase

## ลิงก์ที่ใช้งาน
- [CircleImageView](https://github.com/hdodenhof/CircleImageView.git) - บน GitHub
- [Firebase Realtime Database](https://firebaseopensource.com/projects/firebase/firebaseui-android/database/readme/) - บน Firebase
- [Glide](https://github.com/bumptech/glide.git) - บน GitHub
