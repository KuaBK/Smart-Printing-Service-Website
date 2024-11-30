import React, { useEffect, useRef, useState } from 'react';
import classes from './style.module.css';
import deletePromote from '../../assets/deletePromote.svg'
// import filterimage from '../../assets/filterimage.svg'
// import './styleModule.css';
import Swal from 'sweetalert2/dist/sweetalert2.js'
import 'sweetalert2/src/sweetalert2.scss'
import api from '../../Services/api';
/*Duy done */
// const listUsers = [
//   {
//     id: 1,
//     fullname: "Nguyễn Khắc Duy kun",
//     mssv: "2210517",
//     username: "duykun",
//     password: "123abcs",
//     usertype: "User",
//     detail: "CE"
//   },
//   {
//     id: 2,
//     fullname: "Nguyễn Khắc Duy kun",
//     mssv: "2210517",
//     username: "duykun",
//     password: "123abcs",
//     usertype: "User",
//     detail: "CE"
//   },
//   {
//     id: 3,
//     fullname: "Nguyễn Khắc Duy kun",
//     mssv: "2210517",
//     username: "duykun",
//     password: "123abcs",
//     usertype: "User",
//     detail: "CE"
//   },
//   {
//     id: 4,
//     fullname: "Nguyễn Khắc Duy kun",
//     mssv: "2210517",
//     username: "duykun",
//     password: "123abcs",
//     usertype: "User",
//     detail: "CE"
//   },
//   {
//     id: 5,
//     fullname: "Nguyễn Khắc Duy kun",
//     mssv: "2210517",
//     username: "duykun",
//     password: "123abcs",
//     usertype: "User",
//     detail: "CE"
//   },
//   {
//     id: 6,
//     fullname: "Nguyễn Khắc Duy kun",
//     mssv: "2210517",
//     username: "duykun",
//     password: "123abcs",
//     usertype: "User",
//     detail: "CE"
//   },
//   {
//     id: 7,
//     fullname: "Nguyễn Khắc Duy kun",
//     mssv: "2210517",
//     username: "duykun",
//     password: "123abcs",
//     usertype: "User",
//     detail: "CE"
//   },
//   {
//     id: 8,
//     fullname: "Nguyễn Khắc Duy kun",
//     mssv: "2210517",
//     username: "duykun",
//     password: "123abcs",
//     usertype: "SPSO",
//     detail: "CE"
//   },

// ];



export const HomePageAdmin = () => {
  const nameUser = "Nguyen Van A";
  const [typeUser, setTypeUser] = useState("");                 //Duy done
  const [selectUserType, setSelectUserType] = useState("All");  //Duy done  
  const [listUsers, setListUser] = useState([]);
  const [reload, setReload] = useState(false);
  const inputRefs = useRef([]);
  // const refDD = useRef(null);
  useEffect(() => {
    const fetchPrinter = async () => {
      try {
        const response = await api.get("/users");
        setListUser((response.data).data);
        console.log(response.data.data);
      } catch (error) {
        console.error("Có lỗi xảy ra khi tải dữ liệu:", error);
      }
    };
    fetchPrinter();
  }, [reload]);

  // const submitPromote = async (e) => {
  //   e.preventDefault();
  //   const newPromote = {
  //     semester: e.target[0].value,
  //     pagesFree: e.target[1].value,
  //     startDate: e.target[2].value,
  //     endDate: e.target[3].value,
  //     status: "Hoạt động",
  //     type: e.target[4].selectedOptions[0].value
  //   }
  //   try {
  //     const response = await api.post('/spso/make-discount', {
  //       semester: e.target[0].value,
  //       pagesFree: e.target[1].value,
  //       startDate: e.target[2].value,
  //       expirationDate: e.target[3].value,
  //       // status: "Hoạt động",
  //       isAll: e.target[4].selectedOptions[0].value == 'All' ? false : true
  //     });

  //     console.log(response.data);
  //     Swal.fire({
  //       icon: "success",
  //       title: "Tạo khuyến mãi thành công",
  //       showConfirmButton: false,
  //       timer: 3000
  //     });
  //   }
  //   catch (error) {
  //     Swal.fire({
  //       icon: "error",
  //       title: "Tạo khuyến mãi thất bại",
  //       text: "Vui lòng kiểm tra lại thông tin hoặc thử lại sau.",
  //       showConfirmButton: true,
  //       timer: 3000
  //     });    
  //   }
  
  //   const response = await api.get('/spso/discounts');
  //   setListPromote(response.data);
    
  //   inputRefs.current.forEach(ref => ref.value = '');
  //   // console.log(listPromote);
  // };
  const submitAccount = async (e) => {
    e.preventDefault();
    const newAccount = {
      username: e.target[0].value,
      name: e.target[1].value,
      email: e.target[2].value,
      role: e.target[6].selectedOptions[0].value,
      password: e.target[3].value,
      phone: e.target[5].value,
      mssv: e.target[4].value,
    }

    // setListUser([...listUser, newAccount]);
    
    const res = await api.post("/auth/public/signup",newAccount);
    Swal.fire({
      // position: "top-end",
      icon: "success",
      title: "Tạo tài khoản thành công",
      showConfirmButton: false,
      timer: 3000
    });
    inputRefs.current.forEach(ref => ref.value = '');
    // console.log(listUser);
    setReload(!reload);
  };
  const handleDeleteAccount = (id) => {
    Swal.fire({
      title: "Bạn chắc chắn muốn xóa tài khoản này?",
      text: "Bạn sẽ không thể hoàn tác bước này!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Vẫn xóa!",
      cancelButtonText: "Hủy"
    }).then( async(result) => {
      if (result.isConfirmed) {
        // fetch(`http://localhost:3000/listUser/${id}`, {
        //   method: "DELETE"
        // })
        //   .then(res => res.json())
        //   .then(() => {
        //     setReload(!reload);
        //   })
        // Hàm xóa khuyến mãi
        // 
        // setListUser(listUsers.filter(item => item.id !== id));
        const res = await api.delete(`/users/${id}`);
        
        // console.log(reload);
        Swal.fire({
          title: "Deleted!",
          text: "Bạn đã xóa thành công.",
          showConfirmButton: false,
          icon: "success",
          timer: 3000
        });
        setReload(!reload);
      }
    });

  }
  /*Duy done */
  const handleFilter = (e) => {
    setSelectUserType(e.target.selectedOptions[0].value);
  };

  const handleTypeUser = (e) => {
    setTypeUser(e.target.selectedOptions[0].value);
  };

  const handleUpLoad = (e) => {
    // console.log(e.target.selectedOptions[0].value);
    setupLoad(e.target.selectedOptions[0].value);
  };



  return (
    <div className={classes.container}>
      <div className={classes.info}>
        <div className={classes.info_h}> Hi, {nameUser}</div>
        <p className={classes.info_p}>Hope you have a good day</p>
      </div>
      <div className={classes.search}>
        <div className={classes.search__filter}>

          <select value={selectUserType} onChange={handleFilter} className={classes.filter_HK}>
            <option value="All">All</option>
            <option value="SPSO">SPSO</option>
            <option value="User">User</option>
          </select>
        </div>
        <input className={classes.search__search} placeholder='Search for Library...' />
      </div>
      <div className={classes.table}>
        <table>
          <thead>
            <th>Họ và Tên</th>
            <th>MSSV</th>
            <th>Tên tài khoản</th>
            <th>Loại tài khoản</th>
            <th>SĐT</th>
            <th className={classes.hidden_th}></th>
          </thead>
          <tbody style={{ height: "240px", overflowY: "auto" }}>
            {listUsers?.map((item) => {
              if (selectUserType === "All" || item.usertype === selectUserType) {
                return (
                  <tr key={item.id}>
                    <td>{item.name}</td>
                    <td>{item.mssv}</td>
                    <td>{item.userName}</td>
                    <td>{item.role}</td>
                    <td>{item.phone}</td>
                    <td><img className={classes.hidden_td} src={deletePromote} onClick={() => handleDeleteAccount(item.id)} /></td>
                  </tr>)
              }
            }
            )}
          </tbody>
        </table>
      </div>
      <form onSubmit={submitAccount} className={classes.formPormote}>
        <div className={`${classes.fP__block}`}>
          <div className={classes.block_1}>
            <div className={classes.block_item}>
              <label htmlFor="username">Tên tài khoản</label>
              <input ref={(el) => inputRefs.current[0] = el} className={`${classes.item_box} ${classes.input_date} bg-[#fff]`} type="text" required />
            </div>
            <div className={classes.block_item}>
              <label htmlFor="fullname">Họ và tên</label>
              <input ref={(el) => inputRefs.current[1] = el} className={`${classes.item_box} ${classes.input_date}`} type="text" required />
            </div>
            <div className={classes.block_item}>
              <label className={classes.label_date} htmlFor="mssv">Email</label>
              <input ref={(el) => inputRefs.current[5] = el} className={`${classes.item_box}`} type="text" required />
            </div>
          </div>
          <div className={classes.block_2}>
            <div className={classes.block_item}>
              <label className={classes.label_date} htmlFor="password">Mật khẩu</label>
              <input ref={(el) => inputRefs.current[2] = el} className={`${classes.item_box} ${classes.input_date}`} type="text" required />
            </div>
            <div className={classes.block_item}>
              <label className={classes.label_date} htmlFor="mssv">Mã số sinh viên</label>
              <input ref={(el) => inputRefs.current[3] = el} className={`${classes.item_box}`} type="text" required />
            </div>
            <div className={classes.block_item}>
              <label className={classes.label_date} htmlFor="mssv">Số Điện Thoại</label>
              <input ref={(el) => inputRefs.current[6] = el} className={`${classes.item_box}`} type="text" required />
            </div>
            <div className={classes.block_item}>
              <label htmlFor="usertype">Loại tài khoản</label>
              <select ref={(el) => inputRefs.current[4] = el} className={`${classes.item_box}`} value={typeUser} onChange={handleTypeUser}>
                <option value="STUDENT">User</option>
                <option value="SPSO">SPSO</option>
              </select>
            </div>
          </div>
        </div>
        <button className={classes.btn_save}>Create</button>
      </form>
    </div>
  )
}