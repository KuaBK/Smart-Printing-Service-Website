import React, { useContext, useEffect, useRef, useState } from 'react';
import classes from './style.module.css';
import deletePromote from '../../assets/deletePromote.svg'
import calendar from '../../assets/calendar.svg'
// import filterimage from '../../assets/filterimage.svg'
import arrow from '../../assets/arrow.svg'
import arrowup from '../../assets/arrowup.svg'
// import './styleModule.css';
import Swal from 'sweetalert2/dist/sweetalert2.js'
import 'sweetalert2/src/sweetalert2.scss'
import api from '../../Services/api';
import { GlobalContext } from '../../Context';

const listPromotes = [
  {
    id: 1,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 2,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 3,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 4,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 5,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 6,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 7,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 8,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 9,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 10,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  },
  {
    id: 11,
    semester: "HK231",
    startDate: "22/01/2023",
    endDate: "28/01/2023",
    pagesFree: 100,
    status: "Kết thúc",
    type: "All"
  }
];


const typefile = [
  "PDF", "WORD", "JFIF", "TIFF", "EPS"
];
const dbTypeFile = [
  "PDF", "WORD", "JFIF"
];
export const ConfigSPSO = () => {
  const [typePromote, setTypePromote] = useState("");
  const [selectHK, setSelectHK] = useState("All");
  const [upLoad, setupLoad] = useState(100);
  const [typeFiles, setTypeFiles] = useState(dbTypeFile);
  const [checkDD, setCheckDD] = useState(false);
  const [arroww, setArroww] = useState(arrow);
  const [listPromote, setListPromote] = useState(listPromotes);
  const { profile } = useContext(GlobalContext);
  // const [reload, setReload] = useState(false);
  const inputRefs = useRef([]);
  // const refDD = useRef(null);

  // useEffect(() => {
  //   fetch("http://localhost:3000/listPromote")
  //     .then(res => res.json())
  //     .then(data => {
  //       setListPromote(data);
  //       console.log(data);
  //     }
  //     )
  //   fetch("http://localhost:3000/configFile")
  //     .then(res => res.json())
  //     .then(data => {
  //       setupLoad(data[0].sizefile);
  //       setTypeFiles(data[0].typeFile);
  //       // console.log(data);
  //     })
  // }, [reload])

  const submitPromote = async (e) => {
    e.preventDefault();
    try {

      const response = await api.post('/spso/make-discount', {
        semester: e.target[0].value,
        pagesFree: parseInt(e.target[1].value),
        startDate: e.target[2].value,
        expirationDate: e.target[3].value,
        all: e.target[4].selectedOptions[0].value == 'All' ? true : false,
      });

      console.log(response.data);
      Swal.fire({
        icon: "success",
        title: `Tạo khuyến mãi thành công`,
        showConfirmButton: false,
        timer: 3000
      });
    }
    catch (error) {
      Swal.fire({
        icon: "error",
        title: "Tạo khuyến mãi thất bại",
        text: "Vui lòng kiểm tra lại thông tin hoặc thử lại sau.",
        showConfirmButton: true,
        timer: 3000
      });
    }
    // fetch("http://localhost:3000/listPromote", {
    //   method: "POST",
    //   headers: {
    //     Accept: "application/json",
    //     "Content-Type": "application/json"
    //   },
    //   body: JSON.stringify(newPromote)

    // })
    //   .then(res => res.json())
    //   .then(() => {
    //     setReload(!reload);
    //     Swal.fire({
    //       // position: "top-end",
    //       icon: "success",
    //       title: "Tạo khuyến mãi thành công",
    //       showConfirmButton: false,
    //       timer: 3000
    //     });
    //   })

    const response = await api.get('/spso/discounts');
    setListPromote(response.data);

    inputRefs.current.forEach(ref => ref.value = '');
    // console.log(listPromote);
  };
  const handleDeletePromote = (id) => {
    Swal.fire({
      title: "Bạn chắc chắn muốn xóa khuyến mãi này?",
      text: "Bạn sẽ không thể hoàn tác bước này!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Vẫn xóa!",
      cancelButtonText: "Hủy"
    }).then((result) => {
      if (result.isConfirmed) {
        // fetch(`http://localhost:3000/listPromote/${id}`, {
        //   method: "DELETE"
        // })
        //   .then(res => res.json())
        //   .then(() => {
        //     setReload(!reload);
        //   })
        // Hàm xóa khuyến mãi
        // 
        setListPromote(listPromote.filter(item => item.id !== id));
        Swal.fire({
          title: "Deleted!",
          text: "Bạn đã xóa thành công.",
          showConfirmButton: false,
          icon: "success",
          timer: 3000
        });
      }
    });

  }
  const submitConfig = (e) => {
    e.preventDefault();
    // const newConfigFile = {
    //   sizefile: e.target[0].selectedOptions[0].value,
    //   typeFile: typeFiles
    // }
    // fetch("http://localhost:3000/configFile/1", {
    //   method: "PATCH",
    //   headers: {
    //     Accept: "application/json",
    //     "Content-Type": "application/json"
    //   },
    //   body: JSON.stringify(newConfigFile)
    // })
    //   .then(res => res.json())
    //   .then(() => {
    //     setReload(!reload);
    //     Swal.fire({
    //       // position: "top-end",
    //       icon: "success",
    //       title: "Chỉnh cấu hình thành công",
    //       showConfirmButton: false,
    //       timer: 3000
    //     });
    //   })
    Swal.fire({
      // position: "top-end",
      icon: "success",
      title: "Chỉnh cấu hình thành công",
      showConfirmButton: false,
      timer: 3000
    });
  };
  const handleFilter = (e) => {
    setSelectHK(e.target.selectedOptions[0].value);
  };
  const handleTypePromote = (e) => {
    setTypePromote(e.target.selectedOptions[0].value);
  };
  const handleUpLoad = (e) => {
    // console.log(e.target.selectedOptions[0].value);
    setupLoad(e.target.selectedOptions[0].value);
  };
  const addTF = (e) => {
    // console.log(e);
    setTypeFiles([...typeFiles, e.item]);
  }
  const delTF = (e) => {
    // console.log(e);
    setTypeFiles(typeFiles.filter(tf => tf !== e.item));
  }
  const handledd = () => {
    setArroww(arroww === arrowup ? arrow : arrowup);
    setCheckDD(!checkDD);
    // document.removeEventListener('mousedown', handleClickOutside);
  }
  // console.log(typeFile);

  useEffect(() => {
    const fetchdiscounts = async () => {
      try {
        const response = await api.get(`/spso/discounts`);
        setListPromote(response.data);
        console.log(response.data);
      } catch (error) {
        console.error('Error fetching files:', error);
      }
    };

    fetchdiscounts();
  }, []);
  function formatDateToDDMMYYYY(dateStr) {
    if (dateStr){
      const [year, month, day] = dateStr.split("-");
    return `${day}-${month}-${year}`;
    }else{
      return "1-1-1900";
    }
  }
  return (
    <div className={classes.container}>
      <div className={classes.info}>
        <div className={classes.info_h}> Hi, {profile?.name}</div>
        <p className={classes.info_p}>Hope you have a good day</p>
      </div>
      <div className={classes.search}>
        <div className={classes.search__filter}>

          <select value={selectHK} onChange={handleFilter} className={classes.filter_HK}>
            <option value="All">All</option>
            <option value="HK231">HK231</option>
            <option value="HK232">HK232</option>
            <option value="HK233">HK233</option>
          </select>
        </div>
        {/* <input className={classes.search__search} placeholder='Search for Library...' /> */}
      </div>
      <div className={classes.table}>
        <table>
          <thead>
            <th>Học kì</th>
            <th>Ngày cấp phát</th>
            <th>Ngày kết thúc</th>
            <th>Số trang A4 được cấp</th>
            <th>Mã</th>
          </thead>
          <tbody style={{ height: "240px", overflowY: "auto" }}>
            {listPromote?.map((item) => {
              if (selectHK === "All" || item.semester === selectHK) {
                return (
                  <tr key={item.id}>
                    <td>{item.semester}</td>
                    <td>{formatDateToDDMMYYYY(item?.startDate)}</td>
                    <td>{formatDateToDDMMYYYY(item?.expirationDate)}</td>
                    <td>{item.pagesFree}</td>
                    <td>{item.discountCode}</td>
                  </tr>)
              }
            }
            )}
          </tbody>
        </table>
      </div>
      <form onSubmit={submitPromote} className={classes.formPormote}>
        <div className={`${classes.fP__block} ${classes.fp__block1}`}>
          <div className={classes.block_1}>
            <div className={classes.block_item}>
              <label htmlFor="semester">Học kì</label>
              <input ref={(el) => inputRefs.current[0] = el} className={`${classes.item_box} bg-[#fff]`} type="text" required />
            </div>
            <div className={classes.block_item}>
              <label htmlFor="pagesFree">Số trang cấp</label>
              <input ref={(el) => inputRefs.current[1] = el} className={classes.item_box} type="text" required />
            </div>
          </div>
          <div className={classes.block_item}>
            <label className={classes.label_date} htmlFor="startDate"><img src={calendar} />Thời gian cấp</label>
            <input ref={(el) => inputRefs.current[2] = el} className={`${classes.item_box} ${classes.input_date}`} type="date" required />
          </div>
        </div>
        <div className={`${classes.fP__block} ${classes.fp__block2}`}>
          <div className={classes.block_item}>
            <label className={classes.label_date} htmlFor="expirationDate"><img src={calendar} />Thời gian kết thúc</label>
            <input ref={(el) => inputRefs.current[3] = el} className={`${classes.item_box} ${classes.input_date}`} type="date" required />
          </div>
          <div className={classes.block_2}>
            <div className={classes.block_item}>
              <label htmlFor="type">Mã cấp</label>
              <select ref={(el) => inputRefs.current[4] = el} className={classes.item_box} value={typePromote} onChange={handleTypePromote}>
                <option value="All">All</option>
                <option value="Limted">Limited</option>
              </select>
            </div>
            <button className={classes.btn_save}>Save</button>
          </div>
        </div>
      </form>
      <form onSubmit={submitConfig} className={classes.formConfig}>
        <div className={classes.block_item}>
          <label htmlFor="">Kích thước file upload</label>
          <select className={classes.item_box} value={upLoad} onChange={handleUpLoad}>
            <option value={30}>30MB</option>
            <option value={50}>50MB</option>
            <option value={100}>100MB</option>
          </select>
        </div>
        <div className={classes.block_item}>
          <label htmlFor="">Loại file được upload</label>
          <div className={classes.configFile} onMouseLeave={() => { setArroww(arrow); setCheckDD(false); }}>
            <div className={classes.cf_dislay}>
              <div className={classes.cf_content}>
                {typeFiles.map((item, index) => (
                  <div key={index} onClick={() => delTF({ item })} className={classes.cf_item}>
                    <div className={classes.item_content}>{item}</div>
                  </div>
                ))}
              </div>
              <div className={`${classes.cf_arrow} `} onClick={handledd}><img src={arroww} /></div>
            </div>
            <div className={`${classes.cf_dd} ${checkDD ? classes.appear : classes.disappear}`}>
              <div className={classes.cf_contenthd}>
                {(typefile.filter(item => !new Set(typeFiles).has(item))).map((item, index) => (
                  <div key={index} onClick={() => addTF({ item })} className={classes.cf_itemhd}>
                    <div className={classes.itemhd_content}>{item}</div>
                  </div>
                ))}
              </div>
            </div>

          </div>
        </div>
        <button className={classes.btn_save}>Save</button>
      </form>
    </div>
  )
}