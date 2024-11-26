import React, { useEffect, useState , useContext} from 'react';
import classes from './style.module.css';
import api from '../../Services/api.jsx';
import axios from 'axios';
import "react-toastify/dist/ReactToastify.css";
import { GlobalContext } from '../../Context/index.jsx';
export const ManagePrint = (props) => {
  const [sbi, setSbi] = useState(1);
  const [arrPrinter, setArrPrinter] = useState([]);
  const [applyPrinter, setApplyPrinter] = useState({});
  const getDataPrinter = async () => {
    try {
      const response = await api.get('/printers');
      console.log(response.data);
      setArrPrinter(response.data);
    }
    catch (error) {
      console.error(error);
    }
  }
  const {token, setToken,profile, setProfile, ToastContainer, toast, fetchProfile} = useContext(GlobalContext)
  const submitConfig1 = async (e) => {
    e.preventDefault();

    // Collect form data
    const formData = new FormData(e.target);
    const title = formData.get("tt");
    const khoa = formData.get("khoa");
    const monhoc = formData.get("mh");
    const danhmuc = formData.get("dm");
    const namhoc = formData.get("nh");
    const value = {
      "headline": title,
      "facultyName": khoa,
      "subject": monhoc,
      "category": danhmuc,
      "semester": namhoc
    }
    try {
      const response = await api.patch(`/files/${props.file?.id}`, value);
      console.log(response.data);
      try {
        const response1 = await api.post(`/library/share`, {
          "documentId": props.file?.id,
        })
        console.log(response1.data);
        toast.success("Chia sẻ file thành công");
      } catch (error) {
        console.error(error);
        toast.error("Chia sẻ file thất bại");
      }
      
    } catch (error) {
      console.error(error);
      toast.error("Chia sẻ file thất bại");
    }
  };
  const submitConfig = async (e) => {
    console.log("Submitting config");
    e.preventDefault();
  
    // Collect form data
    const formData = new FormData(e.target);
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

    const pageOption1 = document.querySelector('input[name="pageOption"]:checked')?.value;
    const customRange = document.querySelector('input[name="customPageRange"]')?.value;

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
    const nb = props.file ? props.file.numberOfPages * sbi : 0;
    if (nb > profile.numOfPrintingPages) {
      toast.error("Số trang in vượt quá số trang còn lại");
      return;
    }
    if (applyPrinter) {
      const nbp = applyPrinter.numOfPaper ? applyPrinter.numOfPaper : 0;
      if (nbp < nb * sbi) {
        toast.error("Số trang in vượt quá số trang còn lại của máy in");
        return;
      }

    } else {
      toast.error("Chưa chọn máy in");
      return;
    }
    
    api.post(`/file-configs/post?fileId=${props.file.id}`, {
      "paperSize": paper_size == "Letter" ? "LETTER" : paper_size == "A4" ? "A4" : "A3",
      "paperRange": pageRange,
      "sides": nopageprint == "Single sided" ? "SINGLE" : "DOUBLE",
      "numberOfCopies": parseInt(noprint),
      "layout": layout == "Landscape" ? "LANDSCAPE" : "PORTRAIT",
      "color": colors == "Color" ? true : false,
      "qrCode": qr ? true : false,
      "pageOfSheet": parseInt(pageOfSize),
      "margin": margin,
      "scale": parseInt(scale)
    })
    .then(response => {
      console.log(response.data)
      {qr ? toast.success(`Tạo mã QR thành công ${response.data.data.CodePrint}`) : toast.success("Cấu hình file thành công")}
      fetchProfile();
    })
    .catch(error => {
      toast.error("Cấu hình file thất bại");
    })
    // try {
    //   const response = await api.post('/print-jobs/create', {
    //     "printerId": props.file.id,
    //     "fileConfigId": 10
    //   });
    //   console.log(response.data);
    //   toast.success("In thành công");
    //   fetchProfile();
    // }
    // catch (error) {
    //   console.error(error);
    //   toast.error("In thất bại");
    // }
  };
  const onChangeSbi = (e) => {
    setSbi(e.target.value);
  }
  useEffect(() => {
    console.log(props);
  }, [props]);
  useEffect(() => {
    getDataPrinter();
    setApplyPrinter(arrPrinter ? arrPrinter[0] : {});
  }, []);
  return (
  <div className={`fixed top-0 left-0 bg-[#cacaca13] w-full h-full z-[200] ${props.isHidden ? 'hidden' : ''}`}>
    <ToastContainer/>
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
                    {/* <input type="text" name='dd' className={classes.inputField} required/> */}
                    <select name='dd' className={classes.inputField} onFocus={getDataPrinter} onChange={(e) => setApplyPrinter(arrPrinter?.find(printer => printer.location === e.target.value))} required>
                      {arrPrinter?.map((printer) => (
                        <option key={printer.printerId}>{printer.location}</option>
                      ))}
                    </select>
                  </div>
                  <div className={classes.item_box}>
                    <label>Thông số máy in</label>
                    <input type="text" value={`${applyPrinter? applyPrinter.brand : ''} - ${applyPrinter? applyPrinter.model : ''}`} name='tsi' className={`${classes.inputField} ${classes.longInput} bg-[#e7e7e7b4]`} required/>
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
              <form id='form2' onSubmit={submitConfig1} className={classes.formShare}>
                <label className={classes.page_label}>Share</label>
                <div className={`${classes.section} ${classes.borderit}`}>
                  <div className={classes.item_box1}>
                    <label>Tiêu đề: </label>
                    <input defaultValue={props.file?.documentName} type="text" name='tt' className={`${classes.inputField} ${classes.longInput1}`} required/>
                  </div>
                  <div className={`${classes.section2} ${classes.format}`}>
                    <div className={classes.item_box1}>
                      <label>Khoa: </label>
                      <input type="text" name='khoa' className={`${classes.inputField} ${classes.inputField1}`} required/>
                    </div>
                    <div className={classes.item_box1}>
                      <label>Môn học: </label>
                      <input type="text" name='mh' className={`${classes.inputField} ${classes.inputField1}`} required/>
                    </div>
                  </div>
                  <div className={`${classes.section2} ${classes.format}`}>
                    <div className={classes.item_box1}>
                      <label>Danh mục: </label>
                      <input type="text" name='dm' className={`${classes.inputField} ${classes.inputField1}`} required/>
                    </div>
                    <div className={classes.item_box1}>
                      <label>Năm học: </label>
                      <input type="text" name='nh' className={`${classes.inputField} ${classes.inputField1}`} required/>
                    </div>
                  </div>
                </div>
               
              </form>
            </div>
          </div>
        </div>

        <div className={`${classes.rightside} ${classes.rightsize}`}>
          <div className={`${classes.page_remain} flex flex-row items-center justify-between`}>
            <p className={classes.page_rm}><div className='px-[20px] bg-[#0f6cbf] rounded-[15px] text-[12px] font-bold text-[#fff] align-center items-center text-center h-fit py-[3px]'>Số trang hiện có: </div>  <b className={classes.numberpage}>{profile?.numOfPrintingPages}</b></p>
            {<p className="text-[13px] text-red-500 font-bold"> Số trang in: {props.file ? props.file.numberOfPages * sbi: 0} </p>}
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
