import React, { useState, useEffect, useContext } from 'react'
// import { PDFViewer } from '@react-pdf/viewer'
import classes from './style.module.css'
import Folder_dublicate_duotone from '../../assets/Folder_dublicate_duotone.svg'
import onProgressDot from '../../assets/onProgressDot.svg'
import doneDot from '../../assets/doneDot.svg'
import calendar_Phong from '../../assets/calendar_Phong.svg'
import location from '../../assets/location.svg'
import { GlobalContext } from '../../Context'
import api from '../../Services/api'

// const document = [
//   {
//     id: 1,
//     title: "Tài liệuaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa 1",
//     status: "On Progress",
//     location: "Tầng 1- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 2,
//     title: "Tài liệu 2",
//     status: "On Progress",
//     location: "Tầng 2- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 3,
//     title: "Tài liệu 3",
//     status: "On Progress",
//     location: "Tầng 3- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 4,
//     title: "Tài liệu 4",
//     status: "On Progress",
//     location: "Tầng 4- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 5,
//     title: "Tài liệu 5",
//     status: "On Progress",
//     location: "Tầng 5- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 6,
//     title: "Tài liệu 6",
//     status: "On Progress",
//     location: "Tầng 6- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 7,
//     title: "Tài liệu 7",
//     status: "On Progress",
//     location: "Tầng 7- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 8,
//     title: "Tài liệu 8",
//     status: "On Progress",
//     location: "Tầng 8- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 9,
//     title: "Tài liệu 9",
//     status: "On Progress",
//     location: "Tầng 9- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 10,
//     title: "Tài liệu 10",
//     status: "On Progress",
//     location: "Tầng 10- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 11,
//     title: "Tài liệu 11",
//     status: "On Progress",
//     location: "Tầng 11- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   },
//   {
//     id: 12,
//     title: "Tài liệu 12",
//     status: "On Progress",
//     location: "Tầng 12- GDH6",
//     time: "18:25 - 01/01/2024",
//     quantity: "15",
//     category: "Math",
//     infoPrinter: "BE ơi"
//   }

// ];

export const HomePageUser = () => {
  const [animationClass, setAnimationClass] = useState('');
  const numberHPage = 200;
  const numberPPage = 1000;
  const {profile} = useContext(GlobalContext);

  const [selectHK, setSelectHK] = useState("All");
  const [docDetail, setDocDetail] = useState([]);
  const [preview, setPreview] = useState(false);
  const [document,setDocument] = useState([]);
  const [idPreview,setIdPreview] = useState();

  const handleFilter = (e) => {
    setSelectHK(e.target.selectedOptions[0].value);
  };
  // const handlePreview = (e) => {
  //   // const currentIndex = document.findIndex(doc => doc.documentId === e);
  //   // setDocDetail(document[currentIndex]);
  //   console.log(e);
  //   setIdPreview(e);
  //   setPreview(!preview);
  // }
  const handleClosePreview=()=>{
    setPreview(!preview);
  }
  useEffect(() => {
    const fetchDataID = async () => {
      try {
        const resfile = await api.get(`/files/${idPreview}`);
        setDocDetail(resfile.data);
        console.log(resfile.data);
      } catch (error) {
        console.error('Error fetching files:', error);
      }
    };

    fetchDataID();
  }, [idPreview]);
  useEffect(() => {
    if (preview) {
      setAnimationClass(classes.zoomIn);
      const timer = setTimeout(() => {
        setAnimationClass('');
      }, 300);

      return () => clearTimeout(timer);
    }
  }, [preview]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const resfile = await api.get('/files');
        setDocument(resfile.data);
        console.log(resfile.data);
      } catch (error) {
        console.error('Error fetching files:', error);
      }
    };

    fetchData();
  }, []);
  console.log(profile)
  return (
    <div className={classes.container}>
      <div className={classes.info}>
        <div className={classes.info_name}>
          <div className={classes.info_h}> Hi, {profile?.name}</div>
          <p className={classes.info_p}>Hope you have a good day</p>
        </div>
        <div className={classes.info_page}>
          <div className={classes.info_hPage}>
            <div className={classes.hPage_img}><img src={Folder_dublicate_duotone} /></div>
            <div className={classes.hPage_content}>
              <div className={classes.content_title}>Số trang giấy</div>
              <div className={classes.content_number}>{profile?.numOfPrintingPages}</div>
            </div>
          </div>
          <div className={classes.info_pPage}>
            <div className={classes.pPage_title}>Số trang đã in trong tháng</div>
            <div className={classes.pPage_number}>{profile?.numberPageUsed}</div>
          </div>
        </div>
      </div>
      <div className={classes.filter}>
        <select value={selectHK} onChange={handleFilter} className={classes.filter_HK}>
          <option value="All">All</option>
          <option value="HK231">HK231</option>
          <option value="HK232">HK232</option>
          <option value="HK233">HK233</option>
        </select>
        {/* <select value={selectHK} onChange={handleFilter} className={classes.filter_calendar}>
          <option value="All">All</option>
          <option value="HK231">HK231</option>
          <option value="HK232">HK232</option>
          <option value="HK233">HK233</option>
        </select> */}
      </div>
      <div className={classes.body}>
        <div className={classes.list_doc}>
          {document?.map(item => (
            console.log(item),
            <div key={item.id} className={classes.document} onClick={() => {setIdPreview(item.id); setPreview(!preview)}}>
              <div className={classes.line1}>
                <div className={classes.doc_title}>{item?.documentName}</div>
                {!item.shared  ? (
                  <img className={classes.doc_dot} src={onProgressDot} />
                ) : (
                  <img className={classes.doc_dot} src={doneDot} />
                )}
                {item.shared  ? (
                  <div className={classes.doc_status}>Share</div>
                ) : (
                  <div className={classes.doc_status}>Not Share</div>
                )}
              </div>
              <div className={classes.line2}>
                <img className={classes.doc_locIMG} src={location} />
                <div className={classes.doc_loc}>Tầng 9- GDH6</div>
                <img className={classes.doc_calenIMG} src={calendar_Phong} />
                <div className={classes.doc_time}>18:25 - 01/01/2024</div>
              </div>
              <div className={classes.ellipsis}>. . .</div>
            </div>
          ))}
        </div>
        {preview && (
          <div className={`${classes.preview} ${animationClass}`} >
            <div className={classes.preview_info}>
              <div className={classes.info_title}>{docDetail.documentName}</div>
              <div className={classes.info_tag}>
                {docDetail.shared?(
                  <div className={classes.info_share}>Share</div>
                ):(
                  <div className={classes.info_notShare}>Not Share</div>
                )}
              </div>
              <div className={classes.info_wAndt}>
                <img className={classes.info_locIMG} src={location} />
                <div className={classes.info_loc}>{docDetail?.facultyName}</div>
                <img className={classes.info_calenIMG} src={calendar_Phong} />
                <div className={classes.info_time}>{docDetail?.uploadTime}</div>
              </div>
              <div className={classes.info_quanAndCat}>
                <div className={classes.info_quantity}>
                  <label htmlFor="" className={classes.quantity_label}>Số trang in</label>
                  <div className={classes.quantity_div}>{docDetail?.numberOfPages}</div>
                </div>
                <div className={classes.info_category}>
                  <label htmlFor="" className={classes.category_label}>Danh mục</label>
                  <div className={classes.category_div}>{docDetail?.category}</div>
                </div>
              </div>
              <div className={classes.info_printer}>
                <label htmlFor="" className={classes.printer_label}>Thông tin máy in</label>
                <div className={classes.printer_div}>{docDetail?.infoPrinter}</div>
              </div>
              <button className={classes.button_nav}>Cấu Hình In</button>
            </div>
            <div className={classes.preview_file}>
              {console.log(docDetail)}
              <iframe src={docDetail?.url} ></iframe>
            </div>
            <div className={classes.preview_close} onClick={()=>handleClosePreview()}></div>
          </div>
        )}
      </div>
    </div>

  )
}
