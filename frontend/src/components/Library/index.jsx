import React, { useState, useEffect } from 'react';
import classes from './style.module.css';
import Folder_dublicate_duotone from '../../assets/Folder_dublicate_duotone.svg';
import calendar_Phong from '../../assets/calendar_Phong.svg';
import location from '../../assets/location.svg';
import api from '../../Services/api';
// import ManagePrint from '../ManagePrint';
import { ManagePrint } from '../ManagePrint'

export const Library = () => {
  const [animationClass, setAnimationClass] = useState('');
  const [selectHK, setSelectHK] = useState("All");
  const [docDetail, setDocDetail] = useState([]);
  const [preview, setPreview] = useState(false);
  const [document,setDocument] =useState([]);

  useEffect(() => {
    const fetchLib = async () => {
      console.log("fetching library data...");
      try {
        const response = await api.get("/library/all");
        setDocument(response.data); 
      } catch (error) {
        console.error("Có lỗi xảy ra khi tải dữ liệu:", error); 
      }
    };

    fetchLib(); 
  }, []);



  const handleFilter = (e) => setSelectHK(e.target.value);
  const [fileC, setFileC] = useState([]);

  const handlePreview = async (docId) => {
    const selectedDoc = document.find(doc => doc.documentId === docId);
    try {
      const response = await api.get(`/files/${docId}`);
      console.log(response.data);
      setFileC(response.data);
    } catch (error) {
      console.error('Error fetching files:', error);
    }
    setDocDetail(selectedDoc);
    setPreview(!preview);
  }
  const handleClosePreview = () => setPreview(false);
  const [showConfig, setShowConfig] = useState(true);
  useEffect(() => {
    if (preview) {
      setAnimationClass(classes.zoomIn);
      const timer = setTimeout(() => setAnimationClass(''), 200);
      return () => clearTimeout(timer);
    }
  }, [preview]);

  return (
    <div className={classes.container}>
      <header className={classes.header}>
        <div className={classes.filterAndSearch}>
          <div className={classes.filterSection}>
            <svg className={classes.filterIcon1} xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16" fill="none">
              <path d="M3.60006 1H12.4001C13.1334 1 13.7334 1.6 13.7334 2.33333V3.8C13.7334 4.33333 13.4001 5 13.0667 5.33333L10.2001 7.86667C9.80006 8.2 9.53339 8.86667 9.53339 9.4V12.2667C9.53339 12.6667 9.26672 13.2 8.93339 13.4L8.00006 14C7.13339 14.5333 5.93339 13.9333 5.93339 12.8667V9.33333C5.93339 8.86667 5.66672 8.26667 5.40006 7.93333L2.86672 5.26667C2.53339 4.93333 2.26672 4.33333 2.26672 3.93333V2.4C2.26672 1.6 2.86672 1 3.60006 1Z" stroke="#787486" stroke-width="1.3" stroke-miterlimit="10" stroke-linecap="round" stroke-linejoin="round" />
            </svg>
            <button className={classes.filterButton}>Filter</button>
            <svg className={classes.filterIcon2} xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16" fill="none">
              <path d="M8.00001 11.1999C7.53335 11.1999 7.06668 11.0199 6.71335 10.6666L2.36668 6.31993C2.17335 6.12659 2.17335 5.8066 2.36668 5.61326C2.56001 5.41993 2.88001 5.41993 3.07335 5.61326L7.42001 9.95993C7.74001 10.2799 8.26001 10.2799 8.58001 9.95993L12.9267 5.61326C13.12 5.41993 13.44 5.41993 13.6333 5.61326C13.8267 5.8066 13.8267 6.12659 13.6333 6.31993L9.28668 10.6666C8.93335 11.0199 8.46668 11.1999 8.00001 11.1999Z" fill="#787486" />
            </svg>
          </div>
                <div className={classes.headerSearch}>
                <svg className={classes.searchIcon} xmlns="http://www.w3.org/2000/svg" width="23" height="22" viewBox="0 0 23 22" fill="none">
                  <path d="M11.3416 19.2499C16.1511 19.2499 20.05 15.3511 20.05 10.5416C20.05 5.73211 16.1511 1.83325 11.3416 1.83325C6.53215 1.83325 2.6333 5.73211 2.6333 10.5416C2.6333 15.3511 6.53215 19.2499 11.3416 19.2499Z" stroke="#787486" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
                  <path d="M20.9666 20.1666L19.1333 18.3333" stroke="#787486" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
                </svg>
                <input
                  type="text"
                  className={classes.headerSearchInput}
                  placeholder="Search for Library..."
                />
                </div>
              </div>
              </header>

              <div className={classes.body}>
              <div className="grid grid-cols-3 gap-9 content-start h-[550px] bg-[#F5F5F5] p-[35px] rounded-[25px]">
                {document?.map(item => (
                <div key={item.documentId} className="flex flex-col bg-[#FFFFFF] rounded-[16px] px-[15px] py-[7px] shadow-xl h-[100px]" onClick={() => handlePreview(item.documentId)}>
                  <div className="flex flex-row justify-between items-center h-[40px] w-full mb-[3px]">
                  <div className="w-[40%] font-Inter font-semibold text-sm whitespace-nowrap overflow-hidden text-ellipsis">{item.documentName}</div>
                  <div className="flex flex-row items-center">
                    <img src={calendar_Phong} className="h-[17px] w-[17px]" alt="calendar icon" />
                    <div className="text-[#787486] font-Inter mx-[10px] text-sm">{item.uploadTime}</div>
                    <button onClick={() => handlePreview(item.documentId)} className="text-[#0D062D] text-2xl font-bold mb-[27px]">...</button>
                  </div>
                  </div>
                  <div className="flex flex-row">
                  {item.category&&(<div
                    className={`h-[23px] w-[46px] rounded-[4px] bg-opacity-30 text-xs text-center pt-[2.5px] mr-[12px]
                    bg-[#83C29D33] text-[#68B266]`}
                  >
                    {item.category}
                  </div>)}
                  {item.semester&&(<div
                    className={`h-[23px] w-[46px] rounded-[4px] bg-opacity-30 text-xs text-center pt-[2.5px] mr-[12px]
                    bg-[#74ADDF33] text-[#4957D5] `}
                  >
                    {item.semester}
                  </div>)}
                  
                  </div>
                </div>
                ))}

                {preview && (
                <div className={`${classes.preview} ${animationClass}`}>
                  <div className={classes.preview_info}>
                  <div className={classes.info_title}>{docDetail.documentName}</div>
                  <div className="flex flex-row">
                    <div
                      className={`h-[23px] w-[46px] rounded-[4px] bg-opacity-30 text-xs text-center pt-[2.5px] mr-[12px] bg-[#83C29D33] text-[#68B266]`}
                    >
                      {docDetail.category}
                    </div>
                    <div
                      className={`h-[23px] w-[46px] rounded-[4px] bg-opacity-30 text-xs text-center pt-[2.5px] mr-[12px] bg-[#74ADDF33] text-[#4957D5]`}
                    >
                      {docDetail.semester}
                    </div>
                  </div>
                  <div className={classes.info_wAndt}>
                    <img className={classes.info_calenIMG} src={calendar_Phong} alt="calendar icon" />
                    <div className={classes.info_time}>{docDetail.uploadTime}</div>
                  </div>
                  <div className={classes.info_quanAndCat}>
                    <div className={classes.info_field}>
                    <label className={classes.field_label}>Khoa:</label>
                    <div className={classes.field_div}>{docDetail.facultyName}</div>
                    </div>
                    <div className={classes.info_course}>
                    <label className={classes.course_label}>Môn học:</label>
                    <div className={classes.course_div}>{docDetail.subject}</div>
                    </div>
                  </div>

                  <div className={classes.info_quanAndCat2}>
                    <div className={classes.info_category}>
                    <label className={classes.category_label}>Danh mục:</label>
                    <div className={classes.category_div}>{docDetail.category}</div>
                    </div>
                    <div className={classes.info_year}>
                    <label className={classes.year_label}>Năm học:</label>
                    <div className={classes.year_div}>{docDetail.semester}</div>
                    </div>
                  </div>

                  <button className={classes.button_report}>Báo cáo</button>
                  <button className={classes.button_nav} onClick={() => {setShowConfig(false)}}>Cấu Hình In</button>
                <ManagePrint setIsHidden={setShowConfig} isHidden={showConfig} file={fileC} printservice={true}/>
              </div>
              <div className={classes.preview_file}>
                <iframe src={docDetail.url} title="Document Preview"></iframe>
              </div>
              <div className={classes.preview_close} onClick={handleClosePreview}></div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
