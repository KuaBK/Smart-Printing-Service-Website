import React, { useEffect, useState } from 'react';
import classes from './style.module.css';
import api from '../../Services/api.jsx';
import axios from 'axios';
export const ManagePrint = (props) => {
  // const inputRefs = useRef([]);
  const [sbi, setSbi] = useState(1);
  // const submitConfig = async (e) => {
  //   console.log('submitting config');
  //   e.preventDefault();
  //   // const newPrint = {
  //   //   place: e.target[0].value,
  //   //   printinfo: e.target[1].value,
  //   //   noprint: e.target[2].value,
  //   //   layout: e.target[3].value,
  //   //   color: e.target[4].selectedOptions[0].value,
  //   //   papersize: e.target[5].value,
  //   //   scale: e.target[6].value,
  //   //   nosided: e.target[7].selectedOptions[0].value,
  //   //   qr: e.target[8].checked ? e.target[8].value : null,
  //   //   page: (() => {
  //   //     const selectedOption = e.target.querySelector('input[name="pageOption"]:checked');
  //   //     if (selectedOption.nextElementSibling && selectedOption.nextElementSibling.type === 'text') {
  //   //       return selectedOption.nextElementSibling.value;
  //   //     }
  //   //     return selectedOption.value;
  //   //   })()
  //   // };
  //   try {
  //     const response = await api.post(`/file-configs/post?fileId=${props.file.documentId}`, {
  //       "paperSize": "A3",
  //       "paperRange": "1-10",
  //       "sides": "SINGLE",
  //       "numberOfCopies": 2,
  //       "layout": "PORTRAIT",
  //       "color": true
  //   });
  //     console.log(response.data);
  //     Swal.fire({
  //       icon: "success",
  //       title: "Chọn in thành công",
  //       showConfirmButton: false,
  //       timer: 3000
  //     });
  //   } catch (error) {
  //     console.error(error);
  //     Swal.fire({
  //       icon: "error",
  //       title: "Đã xảy ra lỗi",
  //       showConfirmButton: false,
  //       timer: 3000
  //     });
  //   }
  // };
  const submitConfig = async (e) => {
    console.log("Submitting config");
    e.preventDefault();
  
    // Collect form data
    const formData = new FormData(e.target);
    const title = formData.get("pageOption");
    const place = formData.get("dd");
    const printer = formData.get("tsi");
    const noprint = formData.get("sbi");
    const layout = formData.get("ly");
    const colors = formData.get("cl");
    const paper_size = formData.get("ps");
    const scale = formData.get("sc");
    const nopageprint = formData.get("smi");
    const qr = formData.get("qr");
    const pageOption = formData.get("pageOption");
    const margin = formData.get("mar");
    const pageOfSize = formData.get("pos");

    const pageOption1 = document.querySelector('input[name="pageOption"]:checked').value;
    const customRange = document.querySelector('input[name="customPageRange"]').value;

    const pageRange =
      pageOption1 === "All"
        ? "ALL"
        : pageOption === "Old page only"
        ? "OLD"
        : pageOption === "Even page only"
        ? "EVEN"
        : pageOption === "Custom"
        ? customRange
        : "ALL";

      //   {
      //     "paperSize": "A4",
      //     "paperRange": "ALL",
      //     "sides": "SINGLE",
      //     "numberOfCopies": 5,
      //     "layout": "PORTRAIT",
      //     "color": true,
      //     "qrCode": true,
      //     "pageOfSheet": 1,
      //     "margin":"margin",
      //     "scale": 100
      // }
    api.post(`/file-configs/post?fileId=${props.file.documentId}`, {
      "paperSize": paper_size == "Letter" ? "LETTER" : paper_size == "A4" ? "A4" : "A3",
      "paperRange": pageRange,
      "sides": nopageprint == "Single sided" ? "SINGLE" : "DOUBLE",
      "numberOfCopies": noprint,
      "layout": layout == "Landscape" ? "LANDSCAPE" : "PORTRAIT",
      "color": colors == "Color" ? true : false,
      "qrCode": qr ? true : false,
      "pageOfSheet": parseInt(pageOfSize),
      "margin": margin,
      "scale": parseInt(scale)
    })
    .then(response => {
      console.log(response.data)
    })
    .catch(error => {
      console.log(response.data)
    })
  };
  const onChangeSbi = (e) => {
    setSbi(e.target.value);
  }
  useEffect(() => {
    console.log(props);
  }, [props]);
  return (
  <div className={`fixed top-0 left-0 bg-[#cacaca13] w-full h-full z-[200] ${props.isHidden ? 'hidden' : ''}`}>
    <div className={classes.wholeConfig}>
      <div className={classes.part1}>
        <button className={classes.close} onClick={() => props.setIsHidden(true)}></button>
      </div>
      <div className={classes.con_pre_section}>
        <div className={classes.part2}>
          <div className={classes.formContainer}>
            {/* Left side with settings */}
            <form onSubmit={submitConfig} id='form1' className={classes.formconfig}>
              <div className={classes.leftside1}>
                {/* Location and Printer Info */}
                <div className={classes.section2}>
                  <div className={classes.item_box}>
                    <label>Địa điểm</label>
                    <input type="text" name='dd' className={classes.inputField} required/>
                  </div>
                  <div className={classes.item_box}>
                    <label>Thông số máy in</label>
                    <input type="text" name='tsi' className={`${classes.inputField} ${classes.longInput}`} required/>
                  </div>
                </div>

                {/* Print Settings */}
                <div className={classes.section3}>
                  <div className={classes.item_box}>
                    <label>Số bản in:</label>
                    <input type="text" name='sbi' className={classes.inputField} defaultValue={1} onChange={onChangeSbi} required/>
                  </div>
                  <div className={classes.item_box}>
                    <label>Layout:</label>
                    <select name='ly' className={classes.inputField} required>
                      <option>Landscape</option>
                      <option>Portrait</option>
                    </select>
                  </div>
                  <div className={classes.item_box} >
                    <label>Colors:</label>
                    <select name='cl' className={classes.inputField} required>
                      <option>Black and white</option>
                      <option>Color</option>
                    </select>
                  </div>
                </div>

                {/* Paper and Scale */}
                <div className={classes.section3}>
                  <div className={classes.item_box}>
                    <label>Paper Size</label>
                    <select name='ps' className={classes.inputField} required>
                      <option>Letter</option>
                      <option>A4</option>
                      <option>A3</option>
                    </select>
                  </div>
                  <div className={classes.item_box}>
                    <label>Scale (%)</label>
                    <input name='sc' type="text" className={classes.inputField} required/>
                  </div>
                  <div className={classes.item_box}>
                    <label>Số mặt in:</label>
                    <select name='smi' className={classes.inputField} required>
                      <option>Single sided</option>
                      <option>Double sided</option>
                    </select>
                  </div>
                </div>
              
                {/* QR Code */}
                <div className={classes.section1}>
                  <input name='qr' type="checkbox" />
                  <label> Tạo mã QR nhận</label>
                </div>

                {/* Page Options */}
                <div className={classes.page_container}>
                  <label className={classes.page_label}>Page</label>
                  <div className={`${classes.section2} ${classes.borderit}`}>
                    <div className={`${classes.leftside} `}>
                      {/* <ul className={classes.select_op} >
                        <li><input type="radio" name="pageOption" /> All</li>
                        <li><input type="radio" name="pageOption" /> Old page only</li>
                        <li><input type="radio" name="pageOption" /> Even page only</li>
                        <li><input type="radio" name="pageOption" /> <input type="text" className={classes.inputField} placeholder =" eg 2-7, 8-10"/></li>
                      </ul> */}
                      <fieldset className={`${classes.leftside} select_op`}>
                        <div>
                          <label>
                            <input type="radio" name="pageOption" value="All" />
                            All
                          </label>
                        </div>
                        <div>
                          <label>
                            <input type="radio" name="pageOption" value="Old page only" />
                            Old pages only
                          </label>
                        </div>
                        <div>
                          <label>
                            <input type="radio" name="pageOption" value="Even page only" />
                            Even pages only
                          </label>
                        </div>
                        <div>
                          <label>
                            <input type="radio" name="pageOption" value="Custom" />
                          </label>
                          <input
                            type="text"
                            name="customPageRange"
                            className={classes.inputField}
                            placeholder="e.g., 2-7, 8-10"
                            // disabled
                            onfocus="this.previousElementSibling.checked = true; this.disabled = false;"
                            onblur="if (!this.previousElementSibling.checked) this.disabled = true;"
                          />
                        </div>
                      </fieldset>
                    </div>
                    <div className={`${classes.rightside}`}>
                      <div className={classes.item_box1}>
                        <label>Page of sheet:</label>
                        <input name='pos' type="text" className={classes.inputField} required/>
                      </div>
                      <div className={classes.item_box1}>
                        <label>Margin:</label>
                        <input name='mar' type="text" className={classes.inputField} required/>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </form>
          
            {/* Share Options */}
            <div className={`${classes.page_container} ${classes.leftside2}`}>
              <form id='form2' onSubmit={submitConfig} className={classes.formShare}>
                <label className={classes.page_label}>Share</label>
                <div className={`${classes.section} ${classes.borderit}`}>
                  <div className={classes.item_box1}>
                    <label>Tiêu đề: </label>
                    <input defaultValue={props.file?.documentName} type="text" className={`${classes.inputField} ${classes.longInput1}`} required/>
                  </div>
                  <div className={`${classes.section2} ${classes.format}`}>
                    <div className={classes.item_box1}>
                      <label>Khoa: </label>
                      <input type="text" className={`${classes.inputField} ${classes.inputField1}`} required/>
                    </div>
                    <div className={classes.item_box1}>
                      <label>Môn học: </label>
                      <input type="text" className={`${classes.inputField} ${classes.inputField1}`} required/>
                    </div>
                  </div>
                  <div className={`${classes.section2} ${classes.format}`}>
                    <div className={classes.item_box1}>
                      <label>Danh mục: </label>
                      <input type="text" className={`${classes.inputField} ${classes.inputField1}`} required/>
                    </div>
                    <div className={classes.item_box1}>
                      <label>Năm học: </label>
                      <input type="text" className={`${classes.inputField} ${classes.inputField1}`} required/>
                    </div>
                  </div>
                </div>
               
              </form>
            </div>
          </div>
        </div>

        <div className={`${classes.rightside} ${classes.rightsize}`}>
          <div className={`${classes.page_remain} flex flex-row items-center justify-between`}>
            <p className={classes.page_rm}><div className='px-[20px] bg-[#0f6cbf] rounded-[15px] text-[12px] font-bold text-[#fff] align-center items-center text-center h-fit py-[3px]'>Số trang hiện có: </div>  <b className={classes.numberpage}>10</b></p>
            {<p className="text-[13px] text-red-500 font-bold"> Số trang in: {props.file?.numberOfPages * sbi} </p>}
          </div>
          <iframe
            src={props.file?.url}
            title="file preview"
            className={classes.preview}
          ></iframe>
          <div className='flex align-end flex-row justify-end'>
            <button className={classes.shareButton} form='form2'>Share</button>
            <button className={classes.payButton} form='form1'>Thanh Toán</button>
          </div>
        </div>
      </div>
      {/* Footer with buttons */}
    </div>
    </div>
  );
};
